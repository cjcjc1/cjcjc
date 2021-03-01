package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Objects;

public class SBPSocket extends WebSocketClient {

    public SBPSocket (URI serverUri) {
        super(serverUri, new Draft_6455());
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMessage(String message) {
        JSONObject data = JSONObject.parseObject(message);
        switch((String) Objects.requireNonNull(data.get("code"))){
            case "startSuccess":
                Parameters.setStartStatus(1);
                break;
            case "startFailed":
                Parameters.getSession().close();
                Parameters.setStartStatus(2);
                break;
            case "playerInfo":
                JSONArray playerList1 = (JSONArray) data.get("playerList");
                JSONArray teamList1 = (JSONArray) data.get("teamList");
                String[] pl1 = playerList1.toArray(new String[]{});
                Integer[] tl1 = teamList1.toArray(new Integer[]{});
                int es1 = -1;
                if(!pl1[0].equals("") && tl1[0] != -1){
                    es1 = 0;
                }
                if(!pl1[1].equals("") && tl1[1] != -1){
                    es1 = 1;
                }
                Parameters.setEnemySide(es1);
                if(es1 != -1){
                    Parameters.setEnemyId(pl1[es1]);
                    Parameters.setEnemyTeam(tl1[es1]);
                }
                break;
            case "sideValid":
                int sv = (int) data.get("isValid");
                Parameters.setSideValid(sv);
                if(sv == 1){
                    JSONArray playerList = (JSONArray) data.get("playerList");
                    JSONArray teamList = (JSONArray) data.get("teamList");
                    int es = (int) data.get("enemySide");
                    String[] pl = playerList.toArray(new String[]{});
                    Integer[] tl = teamList.toArray(new Integer[]{});
                    Parameters.setEnemySide(es);
                    Parameters.setEnemyId(pl[es]);
                    Parameters.setEnemyTeam(tl[es]);
                }
                break;
            case "selectedCan":
            case "exchange":
                data.remove("code");
                Parameters.addSelectedCan(data);
                break;
            case "result1":
                if(Objects.equals(data.get("winner"), Parameters.getMyId())){
                    Parameters.setResult(1);
                }else if(Objects.equals(data.get("loser"), Parameters.getMyId())){
                    Parameters.setResult(2);
                }
                Parameters.setFFFlag(1);
                break;
            case "result2":
                Parameters.setResult(((int)data.get("winner") + Parameters.getEnemySide())%2 + 1);
                break;
            case "escape":
                Parameters.setEscapeFlag(1);
                break;
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {
        System.out.println(ex);
        Parameters.setSocketErrFlag(1);
    }
}
