package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity2 extends AppCompatActivity {

    TextView tvbp1,tvbp2,tvbp3,tvbp4,tvbp5,tvbb1,tvbb2,tvbb3,tvbb4,tvbb5,tvrp1,tvrp2,tvrp3,tvrp4,tvrp5,tvrb1,tvrb2,tvrb3,tvrb4,tvrb5,bt,rt,result1,result2;
    Button btnApply11,mode2,newplayer;

    String p1Id,p2Id,p1Score,p2Score;
    int p1Num,p2Num;
    String bb1,bb2,bb3,bb4,bb5,rb1,rb2,rb3,rb4,rb5,bp1,bp2,bp3,bp4,bp5,rp1,rp2,rp3,rp4,rp5;
    int game;
    String flag;

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
        setContentView(R.layout.activity_result2);

        tvbp1 = this.findViewById(R.id.bp1_2);
        tvbp2 = this.findViewById(R.id.bp2_2);
        tvbp3 = this.findViewById(R.id.bp3_2);
        tvbp4 = this.findViewById(R.id.bp4_2);
        tvbp5 = this.findViewById(R.id.bp5_2);
        tvrp1 = this.findViewById(R.id.rp1_2);
        tvrp2 = this.findViewById(R.id.rp2_2);
        tvrp3 = this.findViewById(R.id.rp3_2);
        tvrp4 = this.findViewById(R.id.rp4_2);
        tvrp5 = this.findViewById(R.id.rp5_2);
        tvbb1 = this.findViewById(R.id.bb1_2);
        tvbb2 = this.findViewById(R.id.bb2_2);
        tvbb3 = this.findViewById(R.id.bb3_2);
        tvbb4 = this.findViewById(R.id.bb4_2);
        tvbb5 = this.findViewById(R.id.bb5_2);
        tvrb1 = this.findViewById(R.id.rb1_2);
        tvrb2 = this.findViewById(R.id.rb2_2);
        tvrb3 = this.findViewById(R.id.rb3_2);
        tvrb4 = this.findViewById(R.id.rb4_2);
        tvrb5 = this.findViewById(R.id.rb5_2);
        bt = this.findViewById(R.id.bt_2);
        rt = this.findViewById(R.id.rt_2);
        result1 = this.findViewById(R.id.result_2);
        result2 = this.findViewById(R.id.resultText_2);
        btnApply11 = this.findViewById(R.id.buttonApply11);
        mode2 = this.findViewById(R.id.mode2);
        newplayer = this.findViewById(R.id.newplayer);

        flag = getIntent().getStringExtra("flag");

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
        result2.setText("Game" + game + "结果");

        switch(flag){
            case "4396":
                int r = (int)(Math.random() * 2);
                switch(r){
                    case 0:
                        p1Score = Integer.toString(Integer.parseInt(p1Score) + 1);
                        result1.setText(p1Id + "获胜！当前比分" + p1Score + "-" + p2Score);
                        break;
                    case 1:
                        p2Score = Integer.toString(Integer.parseInt(p2Score) + 1);
                        result1.setText(p2Id + "获胜！当前比分" + p1Score + "-" + p2Score);
                        break;
                }
                break;
            case "blueff":
                switch(game%2){
                    case 0:
                        p1Score = Integer.toString(Integer.parseInt(p1Score) + 1);
                        result1.setText(p1Id + "获胜！当前比分" + p1Score + "-" + p2Score);
                        break;
                    case 1:
                        p2Score = Integer.toString(Integer.parseInt(p2Score) + 1);
                        result1.setText(p2Id + "获胜！当前比分" + p1Score + "-" + p2Score);
                        break;
                }
                break;
            case "redff":
                switch(game%2){
                    case 1:
                        p1Score = Integer.toString(Integer.parseInt(p1Score) + 1);
                        result1.setText(p1Id + "获胜！当前比分" + p1Score + "-" + p2Score);
                        break;
                    case 0:
                        p2Score = Integer.toString(Integer.parseInt(p2Score) + 1);
                        result1.setText(p2Id + "获胜！当前比分" + p1Score + "-" + p2Score);
                        break;
                }
                break;
        }

        tvbb1.setText(getIntent().getStringExtra("bb1"));
        tvbb2.setText(getIntent().getStringExtra("bb2"));
        tvbb3.setText(getIntent().getStringExtra("bb3"));
        tvbb4.setText(getIntent().getStringExtra("bb4"));
        tvbb5.setText(getIntent().getStringExtra("bb5"));
        tvrb1.setText(getIntent().getStringExtra("rb1"));
        tvrb2.setText(getIntent().getStringExtra("rb2"));
        tvrb3.setText(getIntent().getStringExtra("rb3"));
        tvrb4.setText(getIntent().getStringExtra("rb4"));
        tvrb5.setText(getIntent().getStringExtra("rb5"));
        int blueNum,redNum;
        switch(game%2){
            case 1:
                blueNum = p1Num;
                redNum = p2Num;
                break;
            case 0:
                redNum = p1Num;
                blueNum = p2Num;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + game % 2);
        }
        bt.setText(teamInfo[blueNum][0]);
        rt.setText(teamInfo[redNum][0]);
        tvbp1.setText(teamInfo[blueNum][1] + " " + getIntent().getStringExtra("bp1"));
        tvbp2.setText(teamInfo[blueNum][2] + " " + getIntent().getStringExtra("bp2"));
        tvbp3.setText(teamInfo[blueNum][3] + " " + getIntent().getStringExtra("bp3"));
        tvbp4.setText(teamInfo[blueNum][4] + " " + getIntent().getStringExtra("bp4"));
        tvbp5.setText(teamInfo[blueNum][5] + " " + getIntent().getStringExtra("bp5"));
        tvrp1.setText(teamInfo[redNum][1] + " " + getIntent().getStringExtra("rp1"));
        tvrp2.setText(teamInfo[redNum][2] + " " + getIntent().getStringExtra("rp2"));
        tvrp3.setText(teamInfo[redNum][3] + " " + getIntent().getStringExtra("rp3"));
        tvrp4.setText(teamInfo[redNum][4] + " " + getIntent().getStringExtra("rp4"));
        tvrp5.setText(teamInfo[redNum][5] + " " + getIntent().getStringExtra("rp5"));

        btnApply11.setOnClickListener(new ResultActivity2.L11());
        mode2.setOnClickListener(new ResultActivity2.LMode());
        newplayer.setOnClickListener(new ResultActivity2.LNP());
    }

    class L11 implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(ResultActivity2.this, ChooseTeamActivity2.class);
            intent.putExtra("p1Id", p1Id);
            intent.putExtra("p2Id", p2Id);
            intent.putExtra("p1Score", p1Score);
            intent.putExtra("p2Score", p2Score);
            startActivity(intent);
            finish();
        }
    }

    class LMode implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(ResultActivity2.this, ModeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    class LNP implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(ResultActivity2.this, InputIdActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
