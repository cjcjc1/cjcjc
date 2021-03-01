package com.mywebsite.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint(value = "/ws/sbp")
@Component
public class SimulationBPWebSocketServer {

    private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();

    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    private static String[] playerList = new String[]{"",""};  //{red,blue}

    private static int[] teamList = new int[]{-1,-1};

    private static Map<String, Session> sessionMap = new HashMap<>();

    private static int readyNum = 0;

    private static int phase = 0;

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        int oc = onlineCount.incrementAndGet();
        JSONObject jo = new JSONObject();
        if (oc < 3) {
            jo.put("code", "startSuccess");
        } else {
            jo.put("code", "startFailed");
        }
        sendMessage(session, JSON.toJSONString(jo));
        if (oc < 3) {
            JSONObject jo2 = new JSONObject();
            jo2.put("code", "playerInfo");
            jo2.put("playerList", playerList);
            jo2.put("teamList", teamList);
            sendMessage(session, JSON.toJSONString(jo2));
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        sessions.remove(session);
        int oc = onlineCount.decrementAndGet();
        log.info("有连接关闭，当前连接数为：" + oc);
        if(phase == 1){
            JSONObject jo = new JSONObject();
            jo.put("code", "escape");
            sendMessage(JSON.toJSONString(jo));
            init();
        }else if(phase == 0){
            sessions.remove(session);
            onlineCount.decrementAndGet();
            for(String str : sessionMap.keySet().toArray(new String[]{})){
                if(sessionMap.get(str).equals(session)){
                    sessionMap.remove(str);
                    for(int i = 0; i < 2; i++){
                        if(playerList[i].equals(str)){
                            playerList[i] = "";
                            teamList[i] = -1;
                        }
                    }
                }
            }
        }else if(phase == 2){
            init();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        JSONObject msg = JSON.parseObject(message);
        switch((int) msg.get("phase")){
            case 1:
                String id = (String) msg.get("id");
                int team = (int) msg.get("team");
                sessionMap.put(id, session);
                int s = (int) msg.get("side");
                JSONObject jo1 = new JSONObject();
                jo1.put("code", "sideValid");
                if(playerList[s].equals("") && teamList[s] == -1){
                    playerList[s] = id;
                    teamList[s] = team;
                    jo1.put("isValid", 1);
                    jo1.put("playerList", playerList);
                    jo1.put("teamList", teamList);
                    for (Session ss : sessions) {
                        if(ss.isOpen()){
                            if(ss.equals(session)){
                                jo1.put("enemySide", 1 - s);
                            }else if(!ss.equals(session)){
                                jo1.put("enemySide", s);
                            }
                            sendMessage(ss, JSON.toJSONString(jo1));
                        }
                    }
                    if(!playerList[(s + 1)%2].equals("") && teamList[(s + 1)%2] != -1){
                        phase = 1;
                    }
                } else {
                    jo1.put("isValid", 2);
                    sendMessage(session, JSON.toJSONString(jo1));
                }
                break;
            case 2:
                JSONObject jo2 = msg;
                jo2.remove("phase");
                jo2.put("code", "selectedCan");
                sendMessage(JSON.toJSONString(jo2));
                break;
            case 3:
                JSONObject jo3 = msg;
                jo3.remove("phase");
                jo3.put("code", "result1");
                sendMessage(JSON.toJSONString(jo3));
                phase = 2;
                init();
                break;
            case 4:
                msg.remove("phase");
                msg.put("code", "exchange");
                sendMessage(JSON.toJSONString(msg));
                readyNum++;
                if(readyNum == 2){
                    phase = 2;
                    JSONObject jo4 = new JSONObject();
                    jo4.put("code", "result2");
                    int r = (int) (Math.random() * 2);
                    jo4.put("winner", r);
                    sendMessage(JSON.toJSONString(jo4));
                    init();
                }
                break;
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：Session ID：" + session.getId() + "\n" + error.getMessage());
    }

    private void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送消息出错：" + e.getMessage());
        }
    }

    private void sendMessage(String message) {
        for (Session s : sessions) {
            if(s.isOpen()){
                sendMessage(s, message);
            }
        }
    }

    public static void init() throws IOException {
        for (Session s : sessions) {
            s.close();
        }
        onlineCount.set(0);
        sessions.clear();
        playerList = new String[]{"", ""};
        teamList = new int[]{-1,-1};
        sessionMap = new HashMap<>();
        readyNum = 0;
        phase = 0;
    }
}
