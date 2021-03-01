package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView bp1,bp2,bp3,bp4,bp5,bb1,bb2,bb3,bb4,bb5,rp1,rp2,rp3,rp4,rp5,rb1,rb2,rb3,rb4,rb5,bt,rt,result;
    Button btnApply5,mode1;

    String side,flag;
    int num,teamNum,enemyNum;

    String[][] teamInfo = {new String[]{"SKT", "SKTMarin", "SKTBengi", "SKTFaker", "SKTBang", "SKTWolf"},
            new String[]{"DWG", "DWGNuguri", "DWGCanyon", "DWGShowmaker", "DWGGhost", "DWGBeryl"},
            new String[]{"EDG", "EDGkoro1", "EDGClearlove", "EDGPawn", "EDGDeft", "EDGMeiko"},
            new String[]{"RYL", "RYLGodlike", "RYLLucky", "RYLWh1t3zZ", "RYLUzi", "RYLTabe"},
            new String[]{"ROX", "ROXSmeb", "ROXPeanut", "ROXKuro", "ROXPray", "ROXGorilla"},
            new String[]{"IG", "IGTheShy", "IGNing", "IGRookie", "IGJackeylove", "IGBaolan"},
            new String[]{"SSG", "SSGCuvee", "SSGAmbition", "SSGCrown", "SSGRuler", "SSGCoreJJ"},
            new String[]{"DGL", "nrocADGL", "QBTDGL", "VDOGDGL", "pmIDGL", "lyPDGL"}};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        teamNum = Integer.parseInt(getIntent().getStringExtra("teamNum"));
        enemyNum = Integer.parseInt(getIntent().getStringExtra("enemyNum"));

        bp1 = this.findViewById(R.id.bp1);
        bp2 = this.findViewById(R.id.bp2);
        bp3 = this.findViewById(R.id.bp3);
        bp4 = this.findViewById(R.id.bp4);
        bp5 = this.findViewById(R.id.bp5);
        rp1 = this.findViewById(R.id.rp1);
        rp2 = this.findViewById(R.id.rp2);
        rp3 = this.findViewById(R.id.rp3);
        rp4 = this.findViewById(R.id.rp4);
        rp5 = this.findViewById(R.id.rp5);
        bb1 = this.findViewById(R.id.bb1);
        bb2 = this.findViewById(R.id.bb2);
        bb3 = this.findViewById(R.id.bb3);
        bb4 = this.findViewById(R.id.bb4);
        bb5 = this.findViewById(R.id.bb5);
        rb1 = this.findViewById(R.id.rb1);
        rb2 = this.findViewById(R.id.rb2);
        rb3 = this.findViewById(R.id.rb3);
        rb4 = this.findViewById(R.id.rb4);
        rb5 = this.findViewById(R.id.rb5);
        bt = this.findViewById(R.id.bt);
        rt = this.findViewById(R.id.rt);
        result = this.findViewById(R.id.result);
        btnApply5 = this.findViewById(R.id.buttonApply5);
        mode1 = this.findViewById(R.id.mode1);

        flag = getIntent().getStringExtra("flag");

        int r = (int)(Math.random() * 2);
        switch(r){
            case 0:
                result.setText("我们是冠军！");
                break;
            case 1:
                result.setText("GG，精彩的对局");
                break;
        }
        if(flag != null && flag.equals("ff")){
            result.setText("GG，精彩的对局");
        }

        bb1.setText(getIntent().getStringExtra("bb1"));
        bb2.setText(getIntent().getStringExtra("bb2"));
        bb3.setText(getIntent().getStringExtra("bb3"));
        bb4.setText(getIntent().getStringExtra("bb4"));
        bb5.setText(getIntent().getStringExtra("bb5"));
        rb1.setText(getIntent().getStringExtra("rb1"));
        rb2.setText(getIntent().getStringExtra("rb2"));
        rb3.setText(getIntent().getStringExtra("rb3"));
        rb4.setText(getIntent().getStringExtra("rb4"));
        rb5.setText(getIntent().getStringExtra("rb5"));

        side = getIntent().getStringExtra("side");
        switch(side){
            case "blue":
                bt.setText(teamInfo[teamNum][0]);
                rt.setText(teamInfo[enemyNum][0]);
                bp1.setText(teamInfo[teamNum][1] + " " + getIntent().getStringExtra("bp1"));
                bp2.setText(teamInfo[teamNum][2] + " " + getIntent().getStringExtra("bp2"));
                bp3.setText(teamInfo[teamNum][3] + " " + getIntent().getStringExtra("bp3"));
                bp4.setText(teamInfo[teamNum][4] + " " + getIntent().getStringExtra("bp4"));
                bp5.setText(teamInfo[teamNum][5] + " " + getIntent().getStringExtra("bp5"));
                rp1.setText(teamInfo[enemyNum][1] + " " + getIntent().getStringExtra("rp1"));
                rp2.setText(teamInfo[enemyNum][2] + " " + getIntent().getStringExtra("rp2"));
                rp3.setText(teamInfo[enemyNum][3] + " " + getIntent().getStringExtra("rp3"));
                rp4.setText(teamInfo[enemyNum][4] + " " + getIntent().getStringExtra("rp4"));
                rp5.setText(teamInfo[enemyNum][5] + " " + getIntent().getStringExtra("rp5"));
                break;
            case "red":
                bt.setText(teamInfo[enemyNum][0]);
                rt.setText(teamInfo[teamNum][0]);
                bp1.setText(teamInfo[enemyNum][1] + " " + getIntent().getStringExtra("bp1"));
                bp2.setText(teamInfo[enemyNum][2] + " " + getIntent().getStringExtra("bp2"));
                bp3.setText(teamInfo[enemyNum][3] + " " + getIntent().getStringExtra("bp3"));
                bp4.setText(teamInfo[enemyNum][4] + " " + getIntent().getStringExtra("bp4"));
                bp5.setText(teamInfo[enemyNum][5] + " " + getIntent().getStringExtra("bp5"));
                rp1.setText(teamInfo[teamNum][1] + " " + getIntent().getStringExtra("rp1"));
                rp2.setText(teamInfo[teamNum][2] + " " + getIntent().getStringExtra("rp2"));
                rp3.setText(teamInfo[teamNum][3] + " " + getIntent().getStringExtra("rp3"));
                rp4.setText(teamInfo[teamNum][4] + " " + getIntent().getStringExtra("rp4"));
                rp5.setText(teamInfo[teamNum][5] + " " + getIntent().getStringExtra("rp5"));
                break;
        }

        num = Integer.parseInt(getIntent().getStringExtra("num"));
        num++;
        btnApply5.setText("剑指s" + num);
        btnApply5.setOnClickListener(new ResultActivity.L5());
        mode1.setOnClickListener(new ResultActivity.LMode());
    }

    class L5 implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(ResultActivity.this, ChooseTeamActivity.class);
            intent.putExtra("num", Integer.toString(num));
            startActivity(intent);
            finish();
        }
    }

    class LMode implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(ResultActivity.this, ModeActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
