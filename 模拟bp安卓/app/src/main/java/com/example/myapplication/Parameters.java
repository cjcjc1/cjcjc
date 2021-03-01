package com.example.myapplication;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;

public class Parameters{

    private static SBPSocket session;

    private static Context ApplicationContext;

    private static int startStatus = 0; //init:0,true:1,false:2

    private static String myId;

    private static int myTeam;

    private static String enemyId;

    private static int enemyTeam = -1;

    private static int enemySide = -1; //red:0,blue:1

    private static int sideValid = 0; //init:0,true:1,false:2

    private static JSONObject selectedCan = new JSONObject();

    private static int result = 0; //init:0,win:1,lose:2

    private static int ffFlag = 0; //init:0,ff:1

    private static int escapeFlag = 0; //init:0,escape:1

    private static int socketErrFlag = 0; //cor:0,err:1

    public static void init(){
        session = null;
        ApplicationContext = null;
        startStatus = 0;
        myId = null;
        myTeam = -1;
        enemyId = null;
        enemyTeam = -1;
        enemySide = -1;
        sideValid = 0;
        selectedCan = new JSONObject();
        initSelectedCan();
        ffFlag = 0;
        escapeFlag = 0;
        result = 0;
        socketErrFlag = 0;
    }

    public static void setSession(SBPSocket s){
        session = s;
    }

    public static SBPSocket getSession(){
        return session;
    }

    public static void setContext(Context context){
        ApplicationContext = context;
    }

    public static Context getContext(){
        return ApplicationContext;
    }

    public static void setStartStatus(int s){
        startStatus = s;
    }

    public static int getStartStatus(){
        return startStatus;
    }

    public static void setMyId(String s){
        myId = s;
    }

    public static String getMyId(){
        return myId;
    }

    public static void setMyTeam(int t){
        myTeam = t;
    }

    public static int getMyTeam(){
        return myTeam;
    }

    public static void setEnemyId(String s){
        enemyId = s;
    }

    public static String getEnemyId(){
        return enemyId;
    }

    public static void setEnemyTeam(int t){
        enemyTeam = t;
    }

    public static int getEnemyTeam(){
        return enemyTeam;
    }

    public static void setEnemySide(int t){
        enemySide = t;
    }

    public static int getEnemySide(){
        return enemySide;
    }

    public static void setSideValid(int sv){
        sideValid = sv;
    }

    public static int getSideValid(){
        return sideValid;
    }

    public static void addSelectedCan(JSONObject jo){
        selectedCan.putAll(jo);
    }

    public static JSONObject getSelectedCan(){
        return selectedCan;
    }

    public static void setResult(int r){
        result = r;
    }

    public static int getResult(){
        return result;
    }

    private static void initSelectedCan(){
        selectedCan.put("bb1","ban1");
        selectedCan.put("bb2","ban2");
        selectedCan.put("bb3","ban3");
        selectedCan.put("bb4","ban4");
        selectedCan.put("bb5","ban5");
        selectedCan.put("rb1","ban1");
        selectedCan.put("rb2","ban2");
        selectedCan.put("rb3","ban3");
        selectedCan.put("rb4","ban4");
        selectedCan.put("rb5","ban5");
        selectedCan.put("bp1","pick1");
        selectedCan.put("bp2","pick2");
        selectedCan.put("bp3","pick3");
        selectedCan.put("bp4","pick4");
        selectedCan.put("bp5","pick5");
        selectedCan.put("rp1","pick1");
        selectedCan.put("rp2","pick2");
        selectedCan.put("rp3","pick3");
        selectedCan.put("rp4","pick4");
        selectedCan.put("rp5","pick5");
    }

    public static void setFFFlag(int f){
        ffFlag = f;
    }

    public static int getFFFlag(){
        return ffFlag;
    }

    public static void setEscapeFlag(int e){
        escapeFlag = e;
    }

    public static int getEscapeFlag(){
        return escapeFlag;
    }

    public static void setSocketErrFlag(int ef){
        socketErrFlag = ef;
    }

    public static int getSocketErrFlag(){
        return socketErrFlag;
    }
}
