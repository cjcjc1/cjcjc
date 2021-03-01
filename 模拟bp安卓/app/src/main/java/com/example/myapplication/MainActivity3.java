package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity3 extends AppCompatActivity {
    static TextView vs,bp1,bp2,bp3,bp4,bp5,rp1,rp2,rp3,rp4,rp5,bt,rt,bb1,bb2,bb3,bb4,bb5,rb1,rb2,rb3,rb4,rb5,bc,rc;
    static EditText et;
    static Button btnApply3,btnFF,btnRandom;

    static String playerblue,playerred;

    static final String[][] teamInfo = {new String[]{"SKT", "SKTMarin", "SKTBengi", "SKTFaker", "SKTBang", "SKTWolf"},
            new String[]{"DWG", "DWGNuguri", "DWGCanyon", "DWGShowmaker", "DWGGhost", "DWGBeryl"},
            new String[]{"EDG", "EDGkoro1", "EDGClearlove", "EDGPawn", "EDGDeft", "EDGMeiko"},
            new String[]{"RYL", "RYLGodlike", "RYLLucky", "RYLWh1t3zZ", "RYLUzi", "RYLTabe"},
            new String[]{"ROX", "ROXSmeb", "ROXPeanut", "ROXKuro", "ROXPray", "ROXGorilla"},
            new String[]{"IG", "IGTheShy", "IGNing", "IGRookie", "IGJackeylove", "IGBaolan"},
            new String[]{"SSG", "SSGCuvee", "SSGAmbition", "SSGCrown", "SSGRuler", "SSGCoreJJ"},
            new String[]{"DGL", "nrocADGL", "QBTDGL", "VDOGDGL", "pmIDGL", "lyPDGL"}};
    static String[] blueTeam, redTeam;

    static int phase = 0;

    static List<String> allCandidate;

    static List<String> nickname;
    static List<String> corrcandidate;

    static Map<String, String> m1 = new HashMap<>();
    static Map<String, TextView> m2 = new HashMap<>();

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 1:
                    btnApply3.setEnabled(true);
                    btnFF.setEnabled(true);
                    btnRandom.setEnabled(true);

                    if(Parameters.getEnemySide() == 0){
                        playerblue = Parameters.getMyId();
                        playerred = Parameters.getEnemyId();
                        blueTeam = teamInfo[Parameters.getMyTeam()];
                        redTeam = teamInfo[Parameters.getEnemyTeam()];
                        et.setText("禁用一个英雄");
                    } else if(Parameters.getEnemySide() == 1){
                        playerblue = Parameters.getEnemyId();
                        playerred = Parameters.getMyId();
                        blueTeam = teamInfo[Parameters.getEnemyTeam()];
                        redTeam = teamInfo[Parameters.getMyTeam()];
                        enemySelectedCan("bb1",0,"a");
                        bc.setText("coach:" + Parameters.getEnemyId());
                        rc.setText("coach:" + Parameters.getMyId());
                    }

                    vs.setText(blueTeam[0] + " vs " + redTeam[0]);
                    bc.setText("coach:" + playerblue);
                    rc.setText("coach:" + playerred);
                    bp1.setText(blueTeam[1]);
                    bp2.setText(blueTeam[2]);
                    bp3.setText(blueTeam[3]);
                    bp4.setText(blueTeam[4]);
                    bp5.setText(blueTeam[5]);
                    rp1.setText(redTeam[1]);
                    rp2.setText(redTeam[2]);
                    rp3.setText(redTeam[3]);
                    rp4.setText(redTeam[4]);
                    rp5.setText(redTeam[5]);
                    bt.setText(blueTeam[0]);
                    rt.setText(redTeam[0]);
                    break;
                case 2:
                    String[] etTip = {"禁用一个英雄","选用一个英雄","bp已完成!"};
                    String s = ((String[])msg.obj)[0];
                    String es = Parameters.getSelectedCan().getString(s);
                    allCandidate.remove(es);
                    m2.get(s).setText(es);
                    String flag = ((String[])msg.obj)[2];
                    if(flag.equals("a")){
                        btnApply3.setEnabled(true);
                        et.setHint(etTip[Integer.parseInt(((String[])msg.obj)[1])]);
                    }
                    break;
                case 3:
                    String loser = null;
                    if(Parameters.getResult() == 1){
                        loser = Parameters.getEnemyId();
                    } else if (Parameters.getResult() == 2){
                        loser = Parameters.getMyId();
                    }
                    new AlertDialog.Builder(MainActivity3.this)
                            .setTitle("提示")
                            .setMessage(loser + "投降了，点击确定查看结果...")
                            .setCancelable(true)
                            .setPositiveButton("确定", (dialog, which) -> {
                                Intent intent = new Intent(MainActivity3.this, ResultActivity3.class);
                                MainActivity3.this.startActivity(intent);
                                MainActivity3.this.finish();
                            })
                            .show();
                    break;
                case 4:
                    new AlertDialog.Builder(MainActivity3.this)
                            .setTitle("提示")
                            .setMessage("对面逃跑了，点击退出本场游戏...")
                            .setCancelable(true)
                            .setPositiveButton("确定", (dialog, which) -> {
                                Intent intent = new Intent(MainActivity3.this, ModeActivity.class);
                                MainActivity3.this.startActivity(intent);
                                MainActivity3.this.finish();
                            })
                            .show();
                    break;
            }
        }
    };

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Parameters.setContext(this);

        vs = this.findViewById(R.id.vsText_3);
        bp1 = this.findViewById(R.id.bluePick1_3);
        bp2 = this.findViewById(R.id.bluePick2_3);
        bp3 = this.findViewById(R.id.bluePick3_3);
        bp4 = this.findViewById(R.id.bluePick4_3);
        bp5 = this.findViewById(R.id.bluePick5_3);
        rp1 = this.findViewById(R.id.redPick1_3);
        rp2 = this.findViewById(R.id.redPick2_3);
        rp3 = this.findViewById(R.id.redPick3_3);
        rp4 = this.findViewById(R.id.redPick4_3);
        rp5 = this.findViewById(R.id.redPick5_3);
        bt = this.findViewById(R.id.blueTeam_3);
        rt = this.findViewById(R.id.redTeam_3);
        bb1 = this.findViewById(R.id.blueBan1_3);
        bb2 = this.findViewById(R.id.blueBan2_3);
        bb3 = this.findViewById(R.id.blueBan3_3);
        bb4 = this.findViewById(R.id.blueBan4_3);
        bb5 = this.findViewById(R.id.blueBan5_3);
        rb1 = this.findViewById(R.id.redBan1_3);
        rb2 = this.findViewById(R.id.redBan2_3);
        rb3 = this.findViewById(R.id.redBan3_3);
        rb4 = this.findViewById(R.id.redBan4_3);
        rb5 = this.findViewById(R.id.redBan5_3);
        bc = this.findViewById(R.id.bluePlayer_3);
        rc = this.findViewById(R.id.redPlayer_3);

        et = this.findViewById(R.id.editText_3);

        btnApply3 = this.findViewById(R.id.buttonApply3_3);
        btnFF = this.findViewById(R.id.buttonFF_3);
        btnRandom = this.findViewById(R.id.random_3);

        btnApply3.setOnClickListener(new MainActivity3.L13());
        btnFF.setOnClickListener(new MainActivity3.LFF());
        btnRandom.setOnClickListener(new MainActivity3.LR());

        btnApply3.setEnabled(false);
        btnFF.setEnabled(false);
        btnRandom.setEnabled(false);

        init();

        Thread thread = new Thread("Thread1") {
            public void run() {
                while(Parameters.getEnemyId().equals("") && Parameters.getEnemyTeam() == -1){
                    SystemClock.sleep(300);
                }
                Message msg = handler.obtainMessage();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        };
        thread.start();

        Thread ffCheck = new Thread("ffCheck") {
            public void run() {
                while(Parameters.getFFFlag() == 0){
                    try {
                        sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message msg = handler.obtainMessage();
                msg.what = 3;
                handler.sendMessage(msg);
            }
        };
        ffCheck.start();

        Thread escapeCheck = new Thread("escapeCheck") {
            public void run() {
                while(Parameters.getEscapeFlag() == 0){
                    try {
                        sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message msg = handler.obtainMessage();
                msg.what = 4;
                handler.sendMessage(msg);
            }
        };
        escapeCheck.start();
    }

    class L13 implements View.OnClickListener
    {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View view)
        {
            if(phase == 10){
                Intent intent = new Intent(MainActivity3.this, ExchangeActivity3.class);
                startActivity(intent);
                finish();
            }
            String input = et.getText().toString();
            if(nickname.contains(input)) {
                int index1 = nickname.indexOf(input);
                input = corrcandidate.get(index1);
            }
            if(!allCandidate.contains(input) && phase < 10){
                showTipDialog();
            }
            else if(phase != 10){
                TextView[] bb = {bb1,bb2,bb3,bb4,bb5};
                TextView[] rb = {rb1,rb2,rb3,rb4,rb5};
                TextView[] rp = {rp1,rp2,rp3,rp4,rp5};
                String[] etTip = {"禁用一个英雄","选用一个英雄"};
                switch(Parameters.getEnemySide()){
                    case 0:
                        switch(phase){
                            case 0:
                            case 1:
                            case 2:
                                String bbs = selectedCan("bb"+(phase+1),"ban"+(phase+1));
                                bb[phase].setText(bbs);
                                enemySelectedCan("rb"+(phase+1),phase/2,"a");
                                phase++;
                                break;
                            case 3:
                                String bpt1 = selectedCan("bp1","pick1");
                                bb[phase].setText(bpt1);
                                enemySelectedCan("rp1",1,"b");
                                enemySelectedCan("rp2",1,"a");
                                phase++;
                                break;
                            case 4:
                                String bpt2 = selectedCan("bp2","pick2");
                                bp2.setText(bpt2);
                                et.setHint(etTip[1]);
                                phase++;
                                break;
                            case 5:
                                String bpt3 = selectedCan("bp3","pick3");
                                bp3.setText(bpt3);
                                enemySelectedCan("rp3",0,"b");
                                enemySelectedCan("rb4",0,"a");
                                phase++;
                                break;
                            case 6:
                                String bbt4 = selectedCan("bb4","ban4");
                                bb4.setText(bbt4);
                                enemySelectedCan("rb5",0,"a");
                                phase++;
                                break;
                            case 7:
                                String bbt5 = selectedCan("bb5","ban5");
                                bb5.setText(bbt5);
                                enemySelectedCan("rp4",1,"a");
                                phase++;
                                break;
                            case 8:
                                String bpt4 = selectedCan("bp4","pick4");
                                bp4.setText(bpt4);
                                et.setHint(etTip[1]);
                                phase++;
                                break;
                            case 9:
                                String bpt5 = selectedCan("bp5","pick5");
                                bp5.setText(bpt5);
                                enemySelectedCan("rp5",2,"a");
                                et.setEnabled(false);
                                btnRandom.setEnabled(false);
                                phase++;
                                break;
                        }
                        break;
                    case 1:
                        switch(phase){
                            case 0:
                            case 1:
                                String rbt12 = selectedCan("rb"+(phase+1),"ban"+(phase+1));
                                rb[phase].setText(rbt12);
                                enemySelectedCan("bb"+(phase+2),0,"a");
                                phase++;
                                break;
                            case 2:
                                String rbt3 = selectedCan("rb3","ban3");
                                rb3.setText(rbt3);
                                enemySelectedCan("bp1",1,"a");
                                phase++;
                                break;
                            case 3:
                            case 5:
                                String rpt13 = selectedCan("rp"+(phase-2),"pick"+(phase-2));
                                rp[phase - 3].setText(rpt13);
                                et.setHint(etTip[2 - phase/2]);
                                phase++;
                                break;
                            case 4:
                            case 8:
                                String rpt24 = selectedCan("rp"+(phase/2),"pick"+(phase/2));
                                rp[phase/2 - 1].setText(rpt24);
                                enemySelectedCan("bp"+(phase/2),1,"b");
                                enemySelectedCan("bp"+(phase/2+1),1,"a");
                                phase++;
                                break;
                            case 6:
                            case 7:
                                String rbt45 = selectedCan("rb"+(phase-2),"ban"+(phase-2));
                                rb[phase - 3].setText(rbt45);
                                enemySelectedCan("bb"+(phase-2),phase-6,"a");
                                phase++;
                                break;
                            case 9:
                                String rpt5 = selectedCan("rp5","pick5");
                                rp5.setText(rpt5);

                                et.setText("bp已完成！");
                                et.setEnabled(false);
                                btnRandom.setEnabled(false);
                                phase++;
                                break;
                        }
                        break;
                }
            }
        }
    }

    class LFF implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            SBPSocket s = Parameters.getSession();
            JSONObject joff = new JSONObject();
            joff.put("phase", 3);
            joff.put("winner", Parameters.getEnemyId());
            joff.put("loser", Parameters.getMyId());
            s.send(JSON.toJSONString(joff));
        }
    }

    class LR implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            int ran = (int)(Math.random() * allCandidate.size());
            et.setText(allCandidate.get(ran));
        }
    }

    private void showTipDialog(){
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("这个英雄不存在或已经被禁用/选用，请重新输入")
                .setPositiveButton("确定", null)
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String selectedCan(String s1, String s2){
        String input = et.getText().toString();
        JSONObject jo = new JSONObject();
        jo.put("phase", 2);
        jo.put(s1, input);
        SBPSocket s = Parameters.getSession();
        s.send(JSON.toJSONString(jo));
        String str = Parameters.getSelectedCan().getString(s1);
        while(Objects.equals(str, s2)){
            SystemClock.sleep(300);
            str = Parameters.getSelectedCan().getString(s1);
        }
        allCandidate.remove(str);
        et.setText("");
        return str;
    }

    private void enemySelectedCan(String s, int t, String flag){
        et.setHint("等待对方行动...");
        btnApply3.setEnabled(false);
        Thread thread = new Thread("Thread2") {
            @Override
            public void run() {
                while(Parameters.getSelectedCan().getString(s).equals(m1.get(s))){
                    try {
                        sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message msg = handler.obtainMessage();
                msg.what = 2;
                msg.obj = new String[]{s,String.valueOf(t),flag};
                handler.sendMessage(msg);
            }
        };
        thread.start();
    }

    private static void mapInit(){
        m1.put("bb1","ban1");
        m1.put("bb2","ban2");
        m1.put("bb3","ban3");
        m1.put("bb4","ban4");
        m1.put("bb5","ban5");
        m1.put("rb1","ban1");
        m1.put("rb2","ban2");
        m1.put("rb3","ban3");
        m1.put("rb4","ban4");
        m1.put("rb5","ban5");
        m1.put("bp1","pick1");
        m1.put("bp2","pick2");
        m1.put("bp3","pick3");
        m1.put("bp4","pick4");
        m1.put("bp5","pick5");
        m1.put("rp1","pick1");
        m1.put("rp2","pick2");
        m1.put("rp3","pick3");
        m1.put("rp4","pick4");
        m1.put("rp5","pick5");

        m2.put("bb1",bb1);
        m2.put("bb2",bb2);
        m2.put("bb3",bb3);
        m2.put("bb4",bb4);
        m2.put("bb5",bb5);
        m2.put("rb1",rb1);
        m2.put("rb2",rb2);
        m2.put("rb3",rb3);
        m2.put("rb4",rb4);
        m2.put("rb5",rb5);
        m2.put("bp1",bp1);
        m2.put("bp2",bp2);
        m2.put("bp3",bp3);
        m2.put("bp4",bp4);
        m2.put("bp5",bp5);
        m2.put("rp1",rp1);
        m2.put("rp2",rp2);
        m2.put("rp3",rp3);
        m2.put("rp4",rp4);
        m2.put("rp5",rp5);
    }

    private void init(){
        mapInit();
        phase = 0;
        allCandidate = LOLCandidate.getAllCandidate();
        nickname = LOLCandidate.getNickname();
        corrcandidate = LOLCandidate.getCorrCandidate();
        playerblue = null;
        playerred = null;
        blueTeam = null;
        redTeam = null;
    }
}
