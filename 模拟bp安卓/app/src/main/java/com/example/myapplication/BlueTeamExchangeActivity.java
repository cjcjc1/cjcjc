package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BlueTeamExchangeActivity extends AppCompatActivity {
    TextView tv,tv1,tv2,tv3,tv4,tv5;
    Button btnApply9,btn1,btn2,btn3,btn4,btn5;

    String[][] teamInfo = {new String[]{"SKT", "SKTMarin", "SKTBengi", "SKTFaker", "SKTBang", "SKTWolf"},
            new String[]{"DWG", "DWGNuguri", "DWGCanyon", "DWGShowmaker", "DWGGhost", "DWGBeryl"},
            new String[]{"EDG", "EDGkoro1", "EDGClearlove", "EDGPawn", "EDGDeft", "EDGMeiko"},
            new String[]{"RYL", "RYLGodlike", "RYLLucky", "RYLWh1t3zZ", "RYLUzi", "RYLTabe"},
            new String[]{"ROX", "ROXSmeb", "ROXPeanut", "ROXKuro", "ROXPray", "ROXGorilla"},
            new String[]{"IG", "IGTheShy", "IGNing", "IGRookie", "IGJackeylove", "IGBaolan"},
            new String[]{"SSG", "SSGCuvee", "SSGAmbition", "SSGCrown", "SSGRuler", "SSGCoreJJ"},
            new String[]{"DGL", "nrocADGL", "QBTDGL", "VDOGDGL", "pmIDGL", "lyPDGL"}};

    String p1Id,p2Id,p1Score,p2Score;
    int p1Num,p2Num;
    String bb1,bb2,bb3,bb4,bb5,rb1,rb2,rb3,rb4,rb5,bp1,bp2,bp3,bp4,bp5,rp1,rp2,rp3,rp4,rp5;
    int game;
    int count = 0;
    String tmp;
    Button tmpbtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_blue);

        tv = this.findViewById(R.id.Exchange_B);

        tv1 = this.findViewById(R.id.BTop);
        tv2 = this.findViewById(R.id.BJug);
        tv3 = this.findViewById(R.id.BMid);
        tv4 = this.findViewById(R.id.BBot);
        tv5 = this.findViewById(R.id.BSup);

        btn1 = this.findViewById(R.id.BTopPick);
        btn2 = this.findViewById(R.id.BJugPick);
        btn3 = this.findViewById(R.id.BMidPick);
        btn4 = this.findViewById(R.id.BBotPick);
        btn5 = this.findViewById(R.id.BSupPick);

        btnApply9 = this.findViewById(R.id.buttonApply9);

        p1Id = getIntent().getStringExtra("p1Id");
        p2Id = getIntent().getStringExtra("p2Id");
        p1Num = Integer.parseInt(getIntent().getStringExtra("p1Num"));
        p2Num = Integer.parseInt(getIntent().getStringExtra("p2Num"));
        p1Score = getIntent().getStringExtra("p1Score");
        p2Score = getIntent().getStringExtra("p2Score");

        bp1 = getIntent().getStringExtra("bp1");
        bp2 = getIntent().getStringExtra("bp2");
        bp3 = getIntent().getStringExtra("bp3");
        bp4 = getIntent().getStringExtra("bp4");
        bp5 = getIntent().getStringExtra("bp5");
        rp1 = getIntent().getStringExtra("rp1");
        rp2 = getIntent().getStringExtra("rp2");
        rp3 = getIntent().getStringExtra("rp3");
        rp4 = getIntent().getStringExtra("rp4");
        rp5 = getIntent().getStringExtra("rp5");
        bb1 = getIntent().getStringExtra("bb1");
        bb2 = getIntent().getStringExtra("bb2");
        bb3 = getIntent().getStringExtra("bb3");
        bb4 = getIntent().getStringExtra("bb4");
        bb5 = getIntent().getStringExtra("bb5");
        rb1 = getIntent().getStringExtra("rb1");
        rb2 = getIntent().getStringExtra("rb2");
        rb3 = getIntent().getStringExtra("rb3");
        rb4 = getIntent().getStringExtra("rb4");
        rb5 = getIntent().getStringExtra("rb5");

        game = Integer.parseInt(p1Score) + Integer.parseInt(p2Score) + 1;
        switch(game%2){
            case 1:
                tv.setText("请蓝色方玩家" + p1Id + "交换英雄");
                tv1.setText(teamInfo[p1Num][1]);
                tv2.setText(teamInfo[p1Num][2]);
                tv3.setText(teamInfo[p1Num][3]);
                tv4.setText(teamInfo[p1Num][4]);
                tv5.setText(teamInfo[p1Num][5]);
                break;
            case 0:
                tv.setText("请蓝色方玩家" + p2Id + "交换英雄");
                tv1.setText(teamInfo[p2Num][1]);
                tv2.setText(teamInfo[p2Num][2]);
                tv3.setText(teamInfo[p2Num][3]);
                tv4.setText(teamInfo[p2Num][4]);
                tv5.setText(teamInfo[p2Num][5]);
                break;
        }
        btn1.setText(bp1);
        btn2.setText(bp2);
        btn3.setText(bp3);
        btn4.setText(bp4);
        btn5.setText(bp5);

        btn1.setOnClickListener(new BlueTeamExchangeActivity.LExchange());
        btn2.setOnClickListener(new BlueTeamExchangeActivity.LExchange());
        btn3.setOnClickListener(new BlueTeamExchangeActivity.LExchange());
        btn4.setOnClickListener(new BlueTeamExchangeActivity.LExchange());
        btn5.setOnClickListener(new BlueTeamExchangeActivity.LExchange());
        btnApply9.setOnClickListener(new BlueTeamExchangeActivity.L9());
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

    class L9 implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(BlueTeamExchangeActivity.this, RedTeamExchangeActivity.class);
            intent.putExtra("p1Id", p1Id);
            intent.putExtra("p2Id", p2Id);
            intent.putExtra("p2Num", Integer.toString(p2Num));
            intent.putExtra("p1Num", Integer.toString(p1Num));
            intent.putExtra("p1Score", p1Score);
            intent.putExtra("p2Score", p2Score);
            intent.putExtra("bb1", bb1);
            intent.putExtra("bb2", bb2);
            intent.putExtra("bb3", bb3);
            intent.putExtra("bb4", bb4);
            intent.putExtra("bb5", bb5);
            intent.putExtra("rb1", rb1);
            intent.putExtra("rb2", rb2);
            intent.putExtra("rb3", rb3);
            intent.putExtra("rb4", rb4);
            intent.putExtra("rb5", rb5);
            intent.putExtra("bp1", btn1.getText());
            intent.putExtra("bp2", btn2.getText());
            intent.putExtra("bp3", btn3.getText());
            intent.putExtra("bp4", btn4.getText());
            intent.putExtra("bp5", btn5.getText());
            intent.putExtra("rp1", rp1);
            intent.putExtra("rp2", rp2);
            intent.putExtra("rp3", rp3);
            intent.putExtra("rp4", rp4);
            intent.putExtra("rp5", rp5);

            startActivity(intent);
            finish();
        }
    }
}
