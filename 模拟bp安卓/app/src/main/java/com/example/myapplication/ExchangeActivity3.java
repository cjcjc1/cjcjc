package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ExchangeActivity3 extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4,tv5;
    Button btnApply14,btn1,btn2,btn3,btn4,btn5;

    String[][] teamInfo = {new String[]{"SKT", "SKTMarin", "SKTBengi", "SKTFaker", "SKTBang", "SKTWolf"},
            new String[]{"DWG", "DWGNuguri", "DWGCanyon", "DWGShowmaker", "DWGGhost", "DWGBeryl"},
            new String[]{"EDG", "EDGkoro1", "EDGClearlove", "EDGPawn", "EDGDeft", "EDGMeiko"},
            new String[]{"RYL", "RYLGodlike", "RYLLucky", "RYLWh1t3zZ", "RYLUzi", "RYLTabe"},
            new String[]{"ROX", "ROXSmeb", "ROXPeanut", "ROXKuro", "ROXPray", "ROXGorilla"},
            new String[]{"IG", "IGTheShy", "IGNing", "IGRookie", "IGJackeylove", "IGBaolan"},
            new String[]{"SSG", "SSGCuvee", "SSGAmbition", "SSGCrown", "SSGRuler", "SSGCoreJJ"},
            new String[]{"DGL", "nrocADGL", "QBTDGL", "VDOGDGL", "pmIDGL", "lyPDGL"}};

    int teamNum = Parameters.getMyTeam();
    String p1,p2,p3,p4,p5;
    int count = 0;
    String tmp;
    Button tmpbtn;

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 3:
                    String loser = null;
                    if(Parameters.getResult() == 1){
                        loser = Parameters.getEnemyId();
                    } else if (Parameters.getResult() == 2){
                        loser = Parameters.getMyId();
                    }
                    new AlertDialog.Builder(ExchangeActivity3.this)
                            .setTitle("提示")
                            .setMessage(loser + "投降了，点击确定查看结果...")
                            .setCancelable(true)
                            .setPositiveButton("确定", (dialog, which) -> {
                                Intent intent = new Intent(ExchangeActivity3.this, ResultActivity3.class);
                                ExchangeActivity3.this.startActivity(intent);
                                ExchangeActivity3.this.finish();
                            })
                            .show();
                    break;
                case 4:
                    new AlertDialog.Builder(ExchangeActivity3.this)
                            .setTitle("提示")
                            .setMessage("对面逃跑了，点击退出本场游戏...")
                            .setCancelable(true)
                            .setPositiveButton("确定", (dialog, which) -> {
                                Intent intent = new Intent(ExchangeActivity3.this, ModeActivity.class);
                                ExchangeActivity3.this.startActivity(intent);
                                ExchangeActivity3.this.finish();
                            })
                            .show();
                    break;
                default:
                    btnApply14.setText("确定");
                    btnApply14.setEnabled(true);
                    Intent intent = new Intent(ExchangeActivity3.this, ResultActivity3.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange3);
        Parameters.setContext(this);

        tv1 = this.findViewById(R.id.yourTop_3);
        tv2 = this.findViewById(R.id.yourJug_3);
        tv3 = this.findViewById(R.id.yourMid_3);
        tv4 = this.findViewById(R.id.yourBot_3);
        tv5 = this.findViewById(R.id.yourSup_3);

        btn1 = this.findViewById(R.id.yourTopPick_3);
        btn2 = this.findViewById(R.id.yourJugPick_3);
        btn3 = this.findViewById(R.id.yourMidPick_3);
        btn4 = this.findViewById(R.id.yourBotPick_3);
        btn5 = this.findViewById(R.id.yourSupPick_3);

        btnApply14 = this.findViewById(R.id.buttonApply14);

        tv1.setText(teamInfo[teamNum][1]);
        tv2.setText(teamInfo[teamNum][2]);
        tv3.setText(teamInfo[teamNum][3]);
        tv4.setText(teamInfo[teamNum][4]);
        tv5.setText(teamInfo[teamNum][5]);
        switch(Parameters.getEnemySide()){
            case 0:
                p1 = (String)Parameters.getSelectedCan().get("bp1");
                p2 = (String)Parameters.getSelectedCan().get("bp2");
                p3 = (String)Parameters.getSelectedCan().get("bp3");
                p4 = (String)Parameters.getSelectedCan().get("bp4");
                p5 = (String)Parameters.getSelectedCan().get("bp5");
                break;
            case 1:
                p1 = (String)Parameters.getSelectedCan().get("rp1");
                p2 = (String)Parameters.getSelectedCan().get("rp2");
                p3 = (String)Parameters.getSelectedCan().get("rp3");
                p4 = (String)Parameters.getSelectedCan().get("rp4");
                p5 = (String)Parameters.getSelectedCan().get("rp5");
                break;
        }
        btn1.setText(p1);
        btn2.setText(p2);
        btn3.setText(p3);
        btn4.setText(p4);
        btn5.setText(p5);

        btn1.setOnClickListener(new ExchangeActivity3.LExchange());
        btn2.setOnClickListener(new ExchangeActivity3.LExchange());
        btn3.setOnClickListener(new ExchangeActivity3.LExchange());
        btn4.setOnClickListener(new ExchangeActivity3.LExchange());
        btn5.setOnClickListener(new ExchangeActivity3.LExchange());
        btnApply14.setOnClickListener(new ExchangeActivity3.L14());

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

    class LExchange implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Button btn = (Button)view;
            switch(count){
                case 0:
                    tmpbtn = (Button)view;
                    tmp = btn.getText().toString();
                    count++;
                    break;
                case 1:
                    tmpbtn.setText(btn.getText().toString());
                    btn.setText(tmp);
                    count--;
                    break;
            }
        }
    }

    class L14 implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            SBPSocket s = Parameters.getSession();
            JSONObject jo = new JSONObject();
            jo.put("phase", 4);
            switch(Parameters.getEnemySide()){
                case 0:
                    jo.put("bp1", btn1.getText().toString());
                    jo.put("bp2", btn2.getText().toString());
                    jo.put("bp3", btn3.getText().toString());
                    jo.put("bp4", btn4.getText().toString());
                    jo.put("bp5", btn5.getText().toString());
                    break;
                case 1:
                    jo.put("rp1", btn1.getText().toString());
                    jo.put("rp2", btn2.getText().toString());
                    jo.put("rp3", btn3.getText().toString());
                    jo.put("rp4", btn4.getText().toString());
                    jo.put("rp5", btn5.getText().toString());
                    break;
            }
            s.send(JSON.toJSONString(jo));
            btnApply14.setText("等待对方行动...");
            btnApply14.setEnabled(false);
            Thread thread = new Thread("Thread3") {
                public void run() {
                    while(Parameters.getResult() == 0){
                        try {
                            sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                }
            };
            thread.start();
        }
    }
}
