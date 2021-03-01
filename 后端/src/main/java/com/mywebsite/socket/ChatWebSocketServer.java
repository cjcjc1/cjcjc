package com.mywebsite.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint(value = "/ws/chat")
@Component
public class ChatWebSocketServer {

    private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();

    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        int oc = onlineCount.incrementAndGet();
        log.info("有连接加入，当前连接数为：" + oc);
        this.sendMessage("usc");
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        int oc = onlineCount.decrementAndGet();
        log.info("有连接关闭，当前连接数为：" + oc);
        this.sendMessage("usc");
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("来自客户端的消息：" + message);
        JSONObject jo = JSON.parseObject(message);
        jo.put("time", new Date());
        this.sendMessage("msg: " + jo.toJSONString());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：Session ID：" + session.getId() + "\n" + error.getMessage());
    }

    public void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送消息出错：" + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        for (Session s : sessions) {
            if(s.isOpen()){
                sendMessage(s, message);
            }
        }
    }

    public void sendMessage(String sessionId, String message) {
        Session session = null;
        for (Session s : sessions) {
            if(s.getId().equals(sessionId)){
                session = s;
                break;
            }
        }
        if(session != null) {
            this.sendMessage(session, message);
        } else{
            log.warn("没有找到指定的会话：" + sessionId);
        }
    }
}
