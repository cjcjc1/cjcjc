package com.mywebsite.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint(value = "/ws/swl")
@Component
public class SwlWebSocketServer {
    private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();

    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    private static String[] playerList = new String[]{"","",""};

    private static int[] statusList = new int[]{0,0,0};

    private static Map<String, Session> sessionMap = new HashMap<>();

    private static int lordPosition = -1;

    private static int elphase = 0;

    private static int[] leftCard = new int[]{17,17,17};

    private static int abandonCount = 0;

    private static int phase = 0;

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        int oc = onlineCount.incrementAndGet();
        JSONObject jo = new JSONObject();
        if (oc < 4) {
            jo.put("code", "startSuccess");
        } else {
            jo.put("code", "startFailed");
        }
        sendMessage(session, JSON.toJSONString(jo));
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        sessions.remove(session);
        int oc = onlineCount.decrementAndGet();
        log.info("有连接关闭，当前连接数为：" + oc);
        if(phase == 1){
            JSONObject jo = new JSONObject();
            jo.put("code", "gameOver2");
            for(String k : sessionMap.keySet()){
                if(sessionMap.get(k).equals(session)){
                    jo.put("escapePlayer", k);
                    break;
                }
            }
            sendMessage(JSON.toJSONString(jo));
            init();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        JSONObject msg = JSON.parseObject(message);
        String player = "";
        switch((int) msg.get("phase")){
            case 1:
                player = (String) msg.get("player");
                for(int i = 0; i < 3; i++){
                    if(playerList[i].equals("")){
                        playerList[i] = player;
                        sessionMap.put(player, session);
                        break;
                    }
                }
                changeStatus();
                break;
            case 2:
                player = (String) msg.get("player");
                for(int i = 0; i < 3; i++){
                    if(playerList[i].equals(player)){
                        playerList[i] = "";
                        sessionMap.remove(player);
                        break;
                    }
                }
                changeStatus();
                break;
            case 3:
                player = (String) msg.get("player");
                int status = (int) msg.get("status");
                for(int i = 0; i < 3; i++){
                    if(playerList[i].equals(player)){
                        statusList[i] = (status + 1)%2;
                        break;
                    }
                }
                changeStatus();
                JSONObject res3 = new JSONObject();
                res3.put("code", "receiveMyInfo");
                sendMessage(session, JSON.toJSONString(res3));
                if(status == 0){
                    if(Arrays.equals(statusList, new int[]{1, 1, 1})){
                        phase = 1;
                        JSONObject jo = dealCards();
                        jo.put("code", "gameStart");
                        for(int i = 0; i < 3; i++){
                            jo.put("yourPosition", i);
                            sendMessage(sessionMap.get(playerList[i]), JSON.toJSONString(jo));
                        }
                        JSONObject el1 = new JSONObject();
                        el1.put("code", "electLord1");
                        int r3 = (int) (Math.random() * 3);
                        el1.put("firstELPlayer", r3);
                        sendMessage(JSON.toJSONString(el1));
                    }
                }
                break;
            case 4:
                String msg4 = (String) msg.get("message");
                elphase++;
                if(elphase < 4){
                    if(msg4.equals("叫地主") || msg4.equals("抢地主")){
                        lordPosition = (int) msg.get("playerPosition");
                    }
                    JSONObject el2 = new JSONObject();
                    el2.put("code", "electLord2");
                    el2.put("secondELPlayer", ((int) msg.get("playerPosition") + 1)%3);
                    el2.put("message", msg4);
                    sendMessage(JSON.toJSONString(el2));
                }
                if(elphase == 4){
                    if(msg4.equals("叫地主") || msg4.equals("抢地主") || msg4.equals("不叫")){
                        lordPosition = (int) msg.get("playerPosition");
                    }
                    JSONObject li = new JSONObject();
                    li.put("code", "lord");
                    li.put("lordPosition", lordPosition);
                    sendMessage(JSON.toJSONString(li));
                    leftCard[lordPosition] = 20;
                }
                break;
            case 5:
                int app5 = (int) msg.get("activePlayerPosition");
                JSONArray card5 = (JSONArray) msg.get("card");
                JSONObject jo5 = new JSONObject();
                jo5.put("code", "card");
                jo5.put("activePlayerPosition", app5);
                jo5.put("card", card5);
                sendMessage(JSON.toJSONString(jo5));
                abandonCount = 0;
                leftCard[app5] = leftCard[app5] - card5.size();
                if(leftCard[app5] == 0) {
                    phase = 2;
                    JSONObject go5 = new JSONObject();
                    go5.put("code", "gameOver");
                    if(lordPosition == app5){
                        go5.put("winner", "lord");
                    } else {
                        go5.put("winner", "farmer");
                    }
                    sendMessage(JSON.toJSONString(go5));
                    init();
                }
                break;
            case 6:
                JSONObject jo6 = new JSONObject();
                jo6.put("code", "abandon");
                jo6.put("activePlayerPosition", msg.get("activePlayerPosition"));
                sendMessage(JSON.toJSONString(jo6));
                abandonCount++;
                if (abandonCount == 2) {
                    JSONObject te6 = new JSONObject();
                    te6.put("code", "turnEnd");
                    te6.put("activePlayerPosition", msg.get("activePlayerPosition"));
                    sendMessage(JSON.toJSONString(te6));
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

    private void changeStatus(){
        for(int i = 0; i < 3; i++){
            if(!playerList[i].equals("")){
                JSONObject res = new JSONObject();
                res.put("code", "playerInfo");
                res.put("nextPlayer", playerList[(i+1)%3]);
                res.put("prevPlayer", playerList[(i+2)%3]);
                res.put("nextPlayerStatus", statusList[(i+1)%3]);
                res.put("prevPlayerStatus", statusList[(i+2)%3]);
                sendMessage(sessionMap.get(playerList[i]), JSON.toJSONString(res));
            }
        }
    }

    private JSONObject dealCards() {
        String[] card = {"s3","h3","c3","d3","s4","h4","c4","d4","s5","h5","c5","d5","s6","h6","c6","d6","s7","h7","c7","d7","s8","h8","c8","d8","s9","h9","c9","d9","s10","h10","c10","d10","s11","h11","c11","d11","s12","h12","c12","d12","s13","h13","c13","d13","s1","h1","c1","d1","s2","h2","c2","d2","j1","j2"};
        String[][] player = {new String[17],new String[17],new String[17]};
        int[] n = {0,0,0};
        String[] lordCard = new String[3];
        int r1 = (int) (Math.random() * 54);
        int r2 = (int) (Math.random() * 54);
        while(r2 == r1) {
            r2 = (int) (Math.random() * 54);
        }
        int r3 = (int) (Math.random() * 54);
        while(r3 == r1 || r3 == r2) {
            r3 = (int) (Math.random() * 54);
        }
        lordCard[0] = card[r1];
        lordCard[1] = card[r2];
        lordCard[2] = card[r3];
        int r;
        for(int i = 0; i < 54; i++) {
            if(i != r1 && i != r2 && i != r3) {
                r = (int) (Math.random() * 3);
                while(n[r] == 17) {
                    r = (int) (Math.random() * 3);
                }
                player[r][n[r]] = card[i];
                n[r]++;
            }
        }
        JSONObject jo = new JSONObject();
        jo.put("p1", player[0]);
        jo.put("p2", player[1]);
        jo.put("p3", player[2]);
        jo.put("lc", lordCard);
        return jo;
    }

    public static void init() throws IOException {
        for (Session s : sessions) {
            s.close();
        }
        onlineCount.set(0);
        sessions.clear();
        playerList = new String[]{"", "", ""};
        statusList = new int[]{0, 0, 0};
        sessionMap = new HashMap<>();
        lordPosition = -1;
        elphase = 0;
        leftCard = new int[]{17, 17, 17};
        abandonCount = 0;
        phase = 0;
    }
}
