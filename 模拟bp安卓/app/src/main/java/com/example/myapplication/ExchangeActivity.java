package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExchangeActivity extends AppCompatActivity {

    TextView tv1,tv2,tv3,tv4,tv5;
    Button btnApply4,btn1,btn2,btn3,btn4,btn5;

    String[][] teamInfo = {new String[]{"SKT", "SKTMarin", "SKTBengi", "SKTFaker", "SKTBang", "SKTWolf"},
            new String[]{"DWG", "DWGNuguri", "DWGCanyon", "DWGShowmaker", "DWGGhost", "DWGBeryl"},
            new String[]{"EDG", "EDGkoro1", "EDGClearlove", "EDGPawn", "EDGDeft", "EDGMeiko"},
            new String[]{"RYL", "RYLGodlike", "RYLLucky", "RYLWh1t3zZ", "RYLUzi", "RYLTabe"},
            new String[]{"ROX", "ROXSmeb", "ROXPeanut", "ROXKuro", "ROXPray", "ROXGorilla"},
            new String[]{"IG", "IGTheShy", "IGNing", "IGRookie", "IGJackeylove", "IGBaolan"},
            new String[]{"SSG", "SSGCuvee", "SSGAmbition", "SSGCrown", "SSGRuler", "SSGCoreJJ"},
            new String[]{"DGL", "nrocADGL", "QBTDGL", "VDOGDGL", "pmIDGL", "lyPDGL"}};

    int teamNum;
    String p1,p2,p3,p4,p5;
    String side,num;
    String enemyNum,bb1,bb2,bb3,bb4,bb5,rb1,rb2,rb3,rb4,rb5,ep1,ep2,ep3,ep4,ep5;
    int count = 0;
    String tmp;
    Button tmpbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        tv1 = this.findViewById(R.id.yourTop);
        tv2 = this.findViewById(R.id.yourJug);
        tv3 = this.findViewById(R.id.yourMid);
        tv4 = this.findViewById(R.id.yourBot);
        tv5 = this.findViewById(R.id.yourSup);

        btn1 = this.findViewById(R.id.yourTopPick);
        btn2 = this.findViewById(R.id.yourJugPick);
        btn3 = this.findViewById(R.id.yourMidPick);
        btn4 = this.findViewById(R.id.yourBotPick);
        btn5 = this.findViewById(R.id.yourSupPick);

        btnApply4 = this.findViewById(R.id.buttonApply4);

        teamNum = Integer.parseInt(getIntent().getStringExtra("teamNum"));

        side = getIntent().getStringExtra("side");
        switch(side){
            case "blue":
                p1 = getIntent().getStringExtra("bp1");
                p2 = getIntent().getStringExtra("bp2");
                p3 = getIntent().getStringExtra("bp3");
                p4 = getIntent().getStringExtra("bp4");
                p5 = getIntent().getStringExtra("bp5");

                ep1 = getIntent().getStringExtra("rp1");
                ep2 = getIntent().getStringExtra("rp2");
                ep3 = getIntent().getStringExtra("rp3");
                ep4 = getIntent().getStringExtra("rp4");
                ep5 = getIntent().getStringExtra("rp5");
                break;
            case "red":
                p1 = getIntent().getStringExtra("rp1");
                p2 = getIntent().getStringExtra("rp2");
                p3 = getIntent().getStringExtra("rp3");
                p4 = getIntent().getStringExtra("rp4");
                p5 = getIntent().getStringExtra("rp5");

                ep1 = getIntent().getStringExtra("bp1");
                ep2 = getIntent().getStringExtra("bp2");
                ep3 = getIntent().getStringExtra("bp3");
                ep4 = getIntent().getStringExtra("bp4");
                ep5 = getIntent().getStringExtra("bp5");
                break;
        }

        tv1.setText(teamInfo[teamNum][1]);
        tv2.setText(teamInfo[teamNum][2]);
        tv3.setText(teamInfo[teamNum][3]);
        tv4.setText(teamInfo[teamNum][4]);
        tv5.setText(teamInfo[teamNum][5]);
        btn1.setText(p1);
        btn2.setText(p2);
        btn3.setText(p3);
        btn4.setText(p4);
        btn5.setText(p5);

        num = getIntent().getStringExtra("num");

        enemyNum = getIntent().getStringExtra("enemyNum");
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

        btn1.setOnClickListener(new ExchangeActivity.LExchange());
        btn2.setOnClickListener(new ExchangeActivity.LExchange());
        btn3.setOnClickListener(new ExchangeActivity.LExchange());
        btn4.setOnClickListener(new ExchangeActivity.LExchange());
        btn5.setOnClickListener(new ExchangeActivity.LExchange());
        btnApply4.setOnClickListener(new ExchangeActivity.L4());
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

    class L4 implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(ExchangeActivity.this, ResultActivity.class);
            intent.putExtra("num", num);
            intent.putExtra("side", side);
            intent.putExtra("teamNum", Integer.toString(teamNum));
            intent.putExtra("enemyNum", enemyNum);
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

            switch(side){
                case "blue":
                    intent.putExtra("bp1", btn1.getText());
                    intent.putExtra("bp2", btn2.getText());
                    intent.putExtra("bp3", btn3.getText());
                    intent.putExtra("bp4", btn4.getText());
                    intent.putExtra("bp5", btn5.getText());
                    intent.putExtra("rp1", ep1);
                    intent.putExtra("rp2", ep2);
                    intent.putExtra("rp3", ep3);
                    intent.putExtra("rp4", ep4);
                    intent.putExtra("rp5", ep5);
                    break;
                case "red":
                    intent.putExtra("bp1", ep1);
                    intent.putExtra("bp2", ep2);
                    intent.putExtra("bp3", ep3);
                    intent.putExtra("bp4", ep4);
                    intent.putExtra("bp5", ep5);
                    intent.putExtra("rp1", btn1.getText());
                    intent.putExtra("rp2", btn2.getText());
                    intent.putExtra("rp3", btn3.getText());
                    intent.putExtra("rp4", btn4.getText());
                    intent.putExtra("rp5", btn5.getText());
                    break;
            }

            startActivity(intent);
            finish();
        }
    }

}
