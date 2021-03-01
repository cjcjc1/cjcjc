package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    TextView vs,bp1,bp2,bp3,bp4,bp5,rp1,rp2,rp3,rp4,rp5,bt,rt,bb1,bb2,bb3,bb4,bb5,rb1,rb2,rb3,rb4,rb5,bc,rc;
    EditText et;
    Button btnApply3,btnFF,btnRandom;

    String p1Id,p2Id,p1Score,p2Score;
    int p1Num,p2Num;

    String playerblue,playerred;

    String[][] teamInfo = {new String[]{"SKT", "SKTMarin", "SKTBengi", "SKTFaker", "SKTBang", "SKTWolf"},
            new String[]{"DWG", "DWGNuguri", "DWGCanyon", "DWGShowmaker", "DWGGhost", "DWGBeryl"},
            new String[]{"EDG", "EDGkoro1", "EDGClearlove", "EDGPawn", "EDGDeft", "EDGMeiko"},
            new String[]{"RYL", "RYLGodlike", "RYLLucky", "RYLWh1t3zZ", "RYLUzi", "RYLTabe"},
            new String[]{"ROX", "ROXSmeb", "ROXPeanut", "ROXKuro", "ROXPray", "ROXGorilla"},
            new String[]{"IG", "IGTheShy", "IGNing", "IGRookie", "IGJackeylove", "IGBaolan"},
            new String[]{"SSG", "SSGCuvee", "SSGAmbition", "SSGCrown", "SSGRuler", "SSGCoreJJ"},
            new String[]{"DGL", "nrocADGL", "QBTDGL", "VDOGDGL", "pmIDGL", "lyPDGL"}};
    String[] blueTeam, redTeam;

    int phase = 1;
    int game;

    List<String> allCandidate = LOLCandidate.getAllCandidate();

    List<String> nickname = LOLCandidate.getNickname();
    List<String> corrcandidate = LOLCandidate.getCorrCandidate();

    List<String> playerblueban = new ArrayList<>();
    List<String> playerredban = new ArrayList<>();
    List<String> playerbluepick = new ArrayList<>();
    List<String> playerredpick = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        vs = this.findViewById(R.id.vsText_2);
        bp1 = this.findViewById(R.id.bluePick1_2);
        bp2 = this.findViewById(R.id.bluePick2_2);
        bp3 = this.findViewById(R.id.bluePick3_2);
        bp4 = this.findViewById(R.id.bluePick4_2);
        bp5 = this.findViewById(R.id.bluePick5_2);
        rp1 = this.findViewById(R.id.redPick1_2);
        rp2 = this.findViewById(R.id.redPick2_2);
        rp3 = this.findViewById(R.id.redPick3_2);
        rp4 = this.findViewById(R.id.redPick4_2);
        rp5 = this.findViewById(R.id.redPick5_2);
        bt = this.findViewById(R.id.blueTeam_2);
        rt = this.findViewById(R.id.redTeam_2);
        bb1 = this.findViewById(R.id.blueBan1_2);
        bb2 = this.findViewById(R.id.blueBan2_2);
        bb3 = this.findViewById(R.id.blueBan3_2);
        bb4 = this.findViewById(R.id.blueBan4_2);
        bb5 = this.findViewById(R.id.blueBan5_2);
        rb1 = this.findViewById(R.id.redBan1_2);
        rb2 = this.findViewById(R.id.redBan2_2);
        rb3 = this.findViewById(R.id.redBan3_2);
        rb4 = this.findViewById(R.id.redBan4_2);
        rb5 = this.findViewById(R.id.redBan5_2);
        bc = this.findViewById(R.id.bluePlayer);
        rc = this.findViewById(R.id.redPlayer);

        et = this.findViewById(R.id.editText_2);

        btnApply3 = this.findViewById(R.id.buttonApply3_2);
        btnFF = this.findViewById(R.id.buttonFF_2);
        btnRandom = this.findViewById(R.id.random_2);

        p1Id = getIntent().getStringExtra("p1Id");
        p2Id = getIntent().getStringExtra("p2Id");
        p1Num = Integer.parseInt(getIntent().getStringExtra("p1Num"));
        p2Num = Integer.parseInt(getIntent().getStringExtra("p2Num"));
        p1Score = getIntent().getStringExtra("p1Score");
        p2Score = getIntent().getStringExtra("p2Score");

        game = Integer.parseInt(p1Score) + Integer.parseInt(p2Score) + 1;

        switch(game%2){
            case 1:
                blueTeam = teamInfo[p1Num];
                redTeam = teamInfo[p2Num];
                bc.setText("coach:"+p1Id);
                rc.setText("coach:"+p2Id);
                playerblue = p1Id;
                playerred = p2Id;
                break;
            case 0:
                blueTeam = teamInfo[p2Num];
                redTeam = teamInfo[p1Num];
                bc.setText("coach:"+p2Id);
                rc.setText("coach:"+p1Id);
                playerblue = p2Id;
                playerred = p1Id;
                break;
        }

        vs.setText(blueTeam[0] + " vs " + redTeam[0]);
        et.setHint("蓝色方"+ playerblue +"禁用英雄");
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

        btnApply3.setOnClickListener(new MainActivity2.L8());
        btnFF.setOnClickListener(new MainActivity2.LFF());
        btnRandom.setOnClickListener(new MainActivity2.LR());
    }

    class L8 implements View.OnClickListener
    {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View view)
        {
            if(phase == 21){
                Intent intent = new Intent(MainActivity2.this, BlueTeamExchangeActivity.class);
                intent.putExtra("p1Id", p1Id);
                intent.putExtra("p2Id", p2Id);
                intent.putExtra("p2Num", Integer.toString(p2Num));
                intent.putExtra("p1Num", Integer.toString(p1Num));
                intent.putExtra("p1Score", p1Score);
                intent.putExtra("p2Score", p2Score);

                intent.putExtra("bb1", playerblueban.get(0));
                intent.putExtra("bb2", playerblueban.get(1));
                intent.putExtra("bb3", playerblueban.get(2));
                intent.putExtra("bb4", playerblueban.get(3));
                intent.putExtra("bb5", playerblueban.get(4));
                intent.putExtra("rb1", playerredban.get(0));
                intent.putExtra("rb2", playerredban.get(1));
                intent.putExtra("rb3", playerredban.get(2));
                intent.putExtra("rb4", playerredban.get(3));
                intent.putExtra("rb5", playerredban.get(4));
                intent.putExtra("bp1", playerbluepick.get(0));
                intent.putExtra("bp2", playerbluepick.get(1));
                intent.putExtra("bp3", playerbluepick.get(2));
                intent.putExtra("bp4", playerbluepick.get(3));
                intent.putExtra("bp5", playerbluepick.get(4));
                intent.putExtra("rp1", playerredpick.get(0));
                intent.putExtra("rp2", playerredpick.get(1));
                intent.putExtra("rp3", playerredpick.get(2));
                intent.putExtra("rp4", playerredpick.get(3));
                intent.putExtra("rp5", playerredpick.get(4));
                startActivity(intent);
                finish();
            }
            String input = et.getText().toString();
            if(nickname.contains(input)) {
                int index1 = nickname.indexOf(input);
                input = corrcandidate.get(index1);
            }
            if(!allCandidate.contains(input) && phase <= 20){
                showTipDialog();
            }
            else if(phase != 21){
                TextView[] tv = {bb1,rb1,bb2,rb2,bb3,rb3,bp1,rp1,rp2,bp2,bp3,rp3,rb4,bb4,rb5,bb5,rp4,bp4,bp5,rp5};
                tv[phase - 1].setText(input);
                allCandidate.remove(input);
                et.setText("");

                switch(phase){
                    case 1:
                    case 3:
                    case 5:
                    case 14:
                        playerblueban.add(input);
                        et.setHint("红色方"+playerred+"禁用英雄");
                        break;
                    case 16:
                        playerblueban.add(input);
                        et.setHint("红色方"+playerred+"选用英雄");
                        break;
                    case 2:
                    case 4:
                    case 13:
                    case 15:
                        playerredban.add(input);
                        et.setHint("蓝色方"+playerblue+"禁用英雄");
                        break;
                    case 6:
                        playerredban.add(input);
                        et.setHint("蓝色方"+playerblue+"选用英雄");
                        break;
                    case 7:
                    case 11:
                    case 19:
                        playerbluepick.add(input);
                        et.setHint("红色方"+playerred+"选用英雄");
                        break;
                    case 8:
                        playerredpick.add(input);
                        et.setHint("红色方"+playerred+"选用英雄");
                        break;
                    case 9:
                    case 17:
                        playerredpick.add(input);
                        et.setHint("蓝色方"+playerblue+"选用英雄");
                        break;
                    case 10:
                    case 18:
                        playerbluepick.add(input);
                        et.setHint("蓝色方"+playerblue+"选用英雄");
                        break;
                    case 12:
                        playerredpick.add(input);
                        et.setHint("红色方"+playerred+"禁用英雄");
                        break;
                    case 20:
                        playerredpick.add(input);
                        et.setText("bp已完成！");
                        et.setEnabled(false);
                        btnRandom.setEnabled(false);
                        btnFF.setEnabled(false);
                        break;
                }
                phase++;
            }
        }
    }

    class LFF implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(MainActivity2.this, ResultActivity2.class);
            intent.putExtra("p1Id", p1Id);
            intent.putExtra("p2Id", p2Id);
            intent.putExtra("p2Num", Integer.toString(p2Num));
            intent.putExtra("p1Num", Integer.toString(p1Num));
            intent.putExtra("p1Score", p1Score);
            intent.putExtra("p2Score", p2Score);
            switch(phase){
                case 1:
                case 3:
                case 5:
                case 7:
                case 10:
                case 11:
                case 14:
                case 16:
                case 18:
                case 19:
                    intent.putExtra("flag", "blueff");
                    break;
                case 2:
                case 4:
                case 6:
                case 8:
                case 9:
                case 12:
                case 13:
                case 15:
                case 17:
                case 20:
                    intent.putExtra("flag", "redff");
                    break;
            }

            String[] bb = {"bb1","bb2","bb3","bb4","bb5"};
            String[] rb = {"rb1","rb2","rb3","rb4","rb5"};
            String[] bp = {"bp1","bp2","bp3","bp4","bp5"};
            String[] rp = {"rp1","rp2","rp3","rp4","rp5"};
            String[] bluebanlist = {"ban1","ban2","ban3","ban4","ban5"};
            String[] redbanlist = {"ban1","ban2","ban3","ban4","ban5"};
            String[] bluepicklist = {"pick1","pick2","pick3","pick4","pick5"};
            String[] redpicklist = {"pick1","pick2","pick3","pick4","pick5"};
            int s1,s2,s3,s4;
            s1 = playerblueban.size();
            s2 = playerbluepick.size();
            s3 = playerredban.size();
            s4 = playerredpick.size();
            for(int i = 0; i < s1; i++){
                bluebanlist[i] = playerblueban.get(i);
            }
            for(int i = 0; i < s3; i++){
                redbanlist[i] = playerredban.get(i);
            }
            for(int i = 0; i < s2; i++){
                bluepicklist[i] = playerbluepick.get(i);
            }
            for(int i = 0; i < s4; i++){
                redpicklist[i] = playerredpick.get(i);
            }

            intent.putExtra(bb[0], bluebanlist[0]);
            intent.putExtra(bb[1], bluebanlist[1]);
            intent.putExtra(bb[2], bluebanlist[2]);
            intent.putExtra(bb[3], bluebanlist[3]);
            intent.putExtra(bb[4], bluebanlist[4]);
            intent.putExtra(rb[0], redbanlist[0]);
            intent.putExtra(rb[1], redbanlist[1]);
            intent.putExtra(rb[2], redbanlist[2]);
            intent.putExtra(rb[3], redbanlist[3]);
            intent.putExtra(rb[4], redbanlist[4]);
            intent.putExtra(bp[0], bluepicklist[0]);
            intent.putExtra(bp[1], bluepicklist[1]);
            intent.putExtra(bp[2], bluepicklist[2]);
            intent.putExtra(bp[3], bluepicklist[3]);
            intent.putExtra(bp[4], bluepicklist[4]);
            intent.putExtra(rp[0], redpicklist[0]);
            intent.putExtra(rp[1], redpicklist[1]);
            intent.putExtra(rp[2], redpicklist[2]);
            intent.putExtra(rp[3], redpicklist[3]);
            intent.putExtra(rp[4], redpicklist[4]);

            startActivity(intent);
            finish();
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
}