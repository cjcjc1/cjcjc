package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView vs,bp1,bp2,bp3,bp4,bp5,rp1,rp2,rp3,rp4,rp5,bt,rt,bb1,bb2,bb3,bb4,bb5,rb1,rb2,rb3,rb4,rb5;
    EditText et;
    Button btnApply3,btnFF,btnRandom;

    int teamNum,enemyNum;
    String side;

    String[][] teamInfo = {new String[]{"SKT", "SKTMarin", "SKTBengi", "SKTFaker", "SKTBang", "SKTWolf"},
            new String[]{"DWG", "DWGNuguri", "DWGCanyon", "DWGShowmaker", "DWGGhost", "DWGBeryl"},
            new String[]{"EDG", "EDGkoro1", "EDGClearlove", "EDGPawn", "EDGDeft", "EDGMeiko"},
            new String[]{"RYL", "RYLGodlike", "RYLLucky", "RYLWh1t3zZ", "RYLUzi", "RYLTabe"},
            new String[]{"ROX", "ROXSmeb", "ROXPeanut", "ROXKuro", "ROXPray", "ROXGorilla"},
            new String[]{"IG", "IGTheShy", "IGNing", "IGRookie", "IGJackeylove", "IGBaolan"},
            new String[]{"SSG", "SSGCuvee", "SSGAmbition", "SSGCrown", "SSGRuler", "SSGCoreJJ"},
            new String[]{"DGL", "nrocADGL", "QBTDGL", "VDOGDGL", "pmIDGL", "lyPDGL"}};
    String[] blueTeam, redTeam;

    int phase = 0;
    int enemyPickPhase = 0;
    EnemyTeam enTeam = new EnemyTeam();

    List<String> allCandidate = LOLCandidate.getAllCandidate();
    List<Integer> canType = LOLCandidate.getCanType();

    List<String> nickname = LOLCandidate.getNickname();
    List<String> corrcandidate = LOLCandidate.getCorrCandidate();

    List<String> topCandidate = LOLCandidate.getTopCandidate();
    List<String> jugCandidate = LOLCandidate.getJugCandidate();
    List<String> midCandidate = LOLCandidate.getMidCandidate();
    List<String> botCandidate = LOLCandidate.getBotCandidate();
    List<String> supCandidate = LOLCandidate.getSupCandidate();

    List<String> playerblueban = new ArrayList<>();
    List<String> playerredban = new ArrayList<>();
    List<String> playerbluepick = new ArrayList<>();
    List<String> playerredpick = new ArrayList<>();

    List<Object> roleList = new ArrayList<>();
    List<String> role = new ArrayList<>();
    String enemyOrder = "";

    String num;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vs = this.findViewById(R.id.vsText);
        bp1 = this.findViewById(R.id.bluePick1);
        bp2 = this.findViewById(R.id.bluePick2);
        bp3 = this.findViewById(R.id.bluePick3);
        bp4 = this.findViewById(R.id.bluePick4);
        bp5 = this.findViewById(R.id.bluePick5);
        rp1 = this.findViewById(R.id.redPick1);
        rp2 = this.findViewById(R.id.redPick2);
        rp3 = this.findViewById(R.id.redPick3);
        rp4 = this.findViewById(R.id.redPick4);
        rp5 = this.findViewById(R.id.redPick5);
        bt = this.findViewById(R.id.blueTeam);
        rt = this.findViewById(R.id.redTeam);
        bb1 = this.findViewById(R.id.blueBan1);
        bb2 = this.findViewById(R.id.blueBan2);
        bb3 = this.findViewById(R.id.blueBan3);
        bb4 = this.findViewById(R.id.blueBan4);
        bb5 = this.findViewById(R.id.blueBan5);
        rb1 = this.findViewById(R.id.redBan1);
        rb2 = this.findViewById(R.id.redBan2);
        rb3 = this.findViewById(R.id.redBan3);
        rb4 = this.findViewById(R.id.redBan4);
        rb5 = this.findViewById(R.id.redBan5);

        et = this.findViewById(R.id.editText);

        btnApply3 = this.findViewById(R.id.buttonApply3);
        btnFF = this.findViewById(R.id.buttonFF);
        btnRandom = this.findViewById(R.id.random);

        teamNum = Integer.parseInt(getIntent().getStringExtra("teamNum"));
        enemyNum = Integer.parseInt(getIntent().getStringExtra("enemyNum"));
        side = getIntent().getStringExtra("side");

        num = getIntent().getStringExtra("num");

        roleList.add(topCandidate);
        roleList.add(jugCandidate);
        roleList.add(midCandidate);
        roleList.add(botCandidate);
        roleList.add(supCandidate);

        role.add("0");
        role.add("1");
        role.add("2");
        role.add("3");
        role.add("4");

        switch(side){
            case "blue":
                blueTeam = teamInfo[teamNum];
                redTeam = teamInfo[enemyNum];
                break;
            case "red":
                blueTeam = teamInfo[enemyNum];
                redTeam = teamInfo[teamNum];
                int blueBan1Index = (int) (Math.random() * allCandidate.size());
                bb1.setText(allCandidate.get(blueBan1Index));
                playerblueban.add(allCandidate.get(blueBan1Index));
                canType.remove(blueBan1Index);
                allCandidate.remove(blueBan1Index);
                break;
        }

        vs.setText(blueTeam[0] + " vs " + redTeam[0]);
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

        btnApply3.setOnClickListener(new MainActivity.L3());
        btnFF.setOnClickListener(new MainActivity.LFF());
        btnRandom.setOnClickListener(new MainActivity.LR());
    }

    class L3 implements View.OnClickListener
    {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View view)
        {
            if(phase == 10){
                Intent intent = new Intent(MainActivity.this, ExchangeActivity.class);
                intent.putExtra("teamNum", Integer.toString(teamNum));
                intent.putExtra("enemyNum", Integer.toString(enemyNum));
                intent.putExtra("num", num);
                intent.putExtra("side", side);

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

                switch(side){
                    case "blue":
                        intent.putExtra("bp1", playerbluepick.get(0));
                        intent.putExtra("bp2", playerbluepick.get(1));
                        intent.putExtra("bp3", playerbluepick.get(2));
                        intent.putExtra("bp4", playerbluepick.get(3));
                        intent.putExtra("bp5", playerbluepick.get(4));
                        intent.putExtra("rp1", playerredpick.get(enemyOrder.indexOf(48)));
                        intent.putExtra("rp2", playerredpick.get(enemyOrder.indexOf(49)));
                        intent.putExtra("rp3", playerredpick.get(enemyOrder.indexOf(50)));
                        intent.putExtra("rp4", playerredpick.get(enemyOrder.indexOf(51)));
                        intent.putExtra("rp5", playerredpick.get(enemyOrder.indexOf(52)));
                        break;
                    case "red":
                        intent.putExtra("bp1", playerbluepick.get(enemyOrder.indexOf(48)));
                        intent.putExtra("bp2", playerbluepick.get(enemyOrder.indexOf(49)));
                        intent.putExtra("bp3", playerbluepick.get(enemyOrder.indexOf(50)));
                        intent.putExtra("bp4", playerbluepick.get(enemyOrder.indexOf(51)));
                        intent.putExtra("bp5", playerbluepick.get(enemyOrder.indexOf(52)));
                        intent.putExtra("rp1", playerredpick.get(0));
                        intent.putExtra("rp2", playerredpick.get(1));
                        intent.putExtra("rp3", playerredpick.get(2));
                        intent.putExtra("rp4", playerredpick.get(3));
                        intent.putExtra("rp5", playerredpick.get(4));
                        break;
                }
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
            else{
                TextView[] bb = {bb1,bb2,bb3,bb4,bb5};
                TextView[] bp = {bp1,bp2,bp3,bp4,bp5};
                TextView[] rb = {rb1,rb2,rb3,rb4,rb5};
                TextView[] rp = {rp1,rp2,rp3,rp4,rp5};
                String[] etTip = {"禁用一个英雄","选用一个英雄"};
                int rbIndex,bbIndex;
                switch(side){
                    case "blue":
                        switch(phase){
                            case 0:
                            case 1:
                            case 2:
                                playerblueban.add(input);
                                bb[phase].setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);

                                rbIndex = (int) (Math.random() * allCandidate.size());
                                rb[phase].setText(allCandidate.get(rbIndex));
                                playerredban.add(allCandidate.get(rbIndex));
                                canType.remove(rbIndex);
                                allCandidate.remove(rbIndex);

                                et.setHint(etTip[phase/2]);
                                et.setText("");
                                phase++;
                                break;
                            case 3:
                                playerbluepick.add(input);
                                bp1.setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);

                                String rpt1 = enemyPick();
                                rp1.setText(rpt1);
                                playerredpick.add(rpt1);

                                String rpt2 = enemyPick();
                                rp2.setText(rpt2);
                                playerredpick.add(rpt2);

                                et.setHint(etTip[1]);
                                et.setText("");
                                phase++;
                                break;
                            case 4:
                                playerbluepick.add(input);
                                bp2.setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);
                                et.setHint(etTip[1]);
                                et.setText("");
                                phase++;
                                break;
                            case 5:
                                playerbluepick.add(input);
                                bp3.setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);

                                String rpt3 = enemyPick();
                                rp3.setText(rpt3);
                                playerredpick.add(rpt3);

                                rbIndex = (int) (Math.random() * allCandidate.size());
                                rb4.setText(allCandidate.get(rbIndex));
                                playerredban.add(allCandidate.get(rbIndex));
                                canType.remove(rbIndex);
                                allCandidate.remove(rbIndex);

                                et.setHint(etTip[0]);
                                et.setText("");
                                phase++;
                                break;
                            case 6:
                                playerblueban.add(input);
                                bb4.setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);

                                rbIndex = (int) (Math.random() * allCandidate.size());
                                rb5.setText(allCandidate.get(rbIndex));
                                playerredban.add(allCandidate.get(rbIndex));
                                canType.remove(rbIndex);
                                allCandidate.remove(rbIndex);

                                et.setHint(etTip[0]);
                                et.setText("");
                                phase++;
                                break;
                            case 7:
                                playerblueban.add(input);
                                bb5.setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);

                                String rpt4 = enemyPick();
                                rp4.setText(rpt4);
                                playerredpick.add(rpt4);

                                et.setHint(etTip[1]);
                                et.setText("");
                                phase++;
                                break;
                            case 8:
                                playerbluepick.add(input);
                                bp4.setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);
                                et.setHint(etTip[1]);
                                et.setText("");
                                phase++;
                                break;
                            case 9:
                                playerbluepick.add(input);
                                bp5.setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);

                                String rpt5 = enemyPick();
                                rp5.setText(rpt5);
                                playerredpick.add(rpt5);

                                et.setText("bp已完成！");
                                et.setEnabled(false);
                                btnRandom.setEnabled(false);
                                phase++;
                                break;
                        }
                        break;
                    case "red":
                        switch(phase){
                            case 0:
                            case 1:
                                playerredban.add(input);
                                rb[phase].setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);

                                bbIndex = (int) (Math.random() * allCandidate.size());
                                bb[phase + 1].setText(allCandidate.get(bbIndex));
                                playerblueban.add(allCandidate.get(bbIndex));
                                canType.remove(bbIndex);
                                allCandidate.remove(bbIndex);

                                et.setHint(etTip[0]);
                                et.setText("");
                                phase++;
                                break;
                            case 2:
                                playerredban.add(input);
                                rb3.setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);

                                String bpt1 = enemyPick();
                                bp1.setText(bpt1);
                                playerbluepick.add(bpt1);

                                et.setHint(etTip[1]);
                                et.setText("");
                                phase++;
                                break;
                            case 3:
                            case 5:
                                playerredpick.add(input);
                                rp[phase - 3].setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);

                                et.setHint(etTip[2 - phase/2]);
                                et.setText("");
                                phase++;
                                break;
                            case 4:
                            case 8:
                                playerredpick.add(input);
                                rp[phase/2 - 1].setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);

                                String bpt2 = enemyPick();
                                bp[phase/2 - 1].setText(bpt2);
                                playerbluepick.add(bpt2);

                                String bpt3 = enemyPick();
                                bp[phase/2].setText(bpt3);
                                playerbluepick.add(bpt3);

                                et.setHint(etTip[1]);
                                et.setText("");
                                phase++;
                                break;
                            case 6:
                            case 7:
                                playerredban.add(input);
                                rb[phase - 3].setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);

                                bbIndex = (int) (Math.random() * allCandidate.size());
                                bb[phase - 3].setText(allCandidate.get(bbIndex));
                                playerblueban.add(allCandidate.get(bbIndex));
                                canType.remove(bbIndex);
                                allCandidate.remove(bbIndex);

                                et.setHint(etTip[phase - 6]);
                                et.setText("");
                                phase++;
                                break;
                            case 9:
                                playerredpick.add(input);
                                rp5.setText(input);
                                canType.remove(allCandidate.indexOf(input));
                                allCandidate.remove(input);

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
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("teamNum", Integer.toString(teamNum));
            intent.putExtra("enemyNum", Integer.toString(enemyNum));
            intent.putExtra("num", num);
            intent.putExtra("side", side);
            intent.putExtra("flag", "ff");

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

    private String enemyPick(){
        enemyPickPhase++;
        int[] l = {74,53,75,21,41};
        int r = (int) (Math.random() * roleList.size());
        List<String> canList = (List<String>) roleList.get(r);
        int r2 = (int) (Math.random() * 1000);
        int r3 = (int) (Math.random() * canList.size());
        String enemyPickCan;
        int epcIndex = 0;
        int epcType = 0;
        switch(enemyPickPhase){
            case 1:
            case 2:
                while(!allCandidate.contains(canList.get(r3)) || (r2 < 990 && r3 >= l[Integer.parseInt(role.get(r))])){
                    r3 = (int) (Math.random() * canList.size());
                }
                enemyPickCan = canList.get(r3);
                epcIndex = allCandidate.indexOf(enemyPickCan);
                epcType = canType.get(epcIndex);
                switch(epcType){
                    case 1:
                        enTeam.addAd();
                        break;
                    case 2:
                        enTeam.addAp();
                        break;
                    case 3:
                        enTeam.addT();
                        break;
                }
                break;
            case 3:
                switch(enTeam.score){
                    case 0:
                        while(!allCandidate.contains(canList.get(r3)) || (r2 < 990 && r3 >= l[Integer.parseInt(role.get(r))]) || canType.get(allCandidate.indexOf(canList.get(r3))) == 4){
                            r3 = (int) (Math.random() * canList.size());
                        }
                        enemyPickCan = canList.get(r3);
                        epcIndex = allCandidate.indexOf(enemyPickCan);
                        epcType = canType.get(epcIndex);
                        switch(epcType){
                            case 1:
                                enTeam.addAd();
                                break;
                            case 2:
                                enTeam.addAp();
                                break;
                            case 3:
                                enTeam.addT();
                                break;
                        }
                        break;
                    default:
                        while(!allCandidate.contains(canList.get(r3)) || (r2 < 990 && r3 >= l[Integer.parseInt(role.get(r))])){
                            r3 = (int) (Math.random() * canList.size());
                        }
                        enemyPickCan = canList.get(r3);
                        epcIndex = allCandidate.indexOf(enemyPickCan);
                        epcType = canType.get(epcIndex);
                        switch(epcType){
                            case 1:
                                enTeam.addAd();
                                break;
                            case 2:
                                enTeam.addAp();
                                break;
                            case 3:
                                enTeam.addT();
                                break;
                        }
                        break;
                }
                break;
            case 4:
                switch(enTeam.score){
                    case 1:
                        while(!allCandidate.contains(canList.get(r3)) || (r2 < 990 && r3 >= l[Integer.parseInt(role.get(r))]) || canType.get(allCandidate.indexOf(canList.get(r3))) == 1 || canType.get(allCandidate.indexOf(canList.get(r3))) == 4){
                            r3 = (int) (Math.random() * canList.size());
                        }
                        enemyPickCan = canList.get(r3);
                        epcIndex = allCandidate.indexOf(enemyPickCan);
                        epcType = canType.get(epcIndex);
                        switch(epcType){
                            case 2:
                                enTeam.addAp();
                                break;
                            case 3:
                                enTeam.addT();
                                break;
                        }
                        break;
                    case 2:
                        while(!allCandidate.contains(canList.get(r3)) || (r2 < 990 && r3 >= l[Integer.parseInt(role.get(r))]) || canType.get(allCandidate.indexOf(canList.get(r3))) == 2 || canType.get(allCandidate.indexOf(canList.get(r3))) == 4){
                            r3 = (int) (Math.random() * canList.size());
                        }
                        enemyPickCan = canList.get(r3);
                        epcIndex = allCandidate.indexOf(enemyPickCan);
                        epcType = canType.get(epcIndex);
                        switch(epcType){
                            case 1:
                                enTeam.addAd();
                                break;
                            case 3:
                                enTeam.addT();
                                break;
                        }
                        break;
                    case 4:
                        while(!allCandidate.contains(canList.get(r3)) || (r2 < 990 && r3 >= l[Integer.parseInt(role.get(r))]) || canType.get(allCandidate.indexOf(canList.get(r3))) == 3 || canType.get(allCandidate.indexOf(canList.get(r3))) == 4){
                            r3 = (int) (Math.random() * canList.size());
                        }
                        enemyPickCan = canList.get(r3);
                        epcIndex = allCandidate.indexOf(enemyPickCan);
                        epcType = canType.get(epcIndex);
                        switch(epcType){
                            case 1:
                                enTeam.addAd();
                                break;
                            case 2:
                                enTeam.addAp();
                                break;
                        }
                        break;
                    default:
                        while(!allCandidate.contains(canList.get(r3)) || (r2 < 990 && r3 >= l[Integer.parseInt(role.get(r))])){
                            r3 = (int) (Math.random() * canList.size());
                        }
                        enemyPickCan = canList.get(r3);
                        epcIndex = allCandidate.indexOf(enemyPickCan);
                        epcType = canType.get(epcIndex);
                        switch(epcType){
                            case 1:
                                enTeam.addAd();
                                break;
                            case 2:
                                enTeam.addAp();
                                break;
                            case 3:
                                enTeam.addT();
                                break;
                        }
                        break;
                }
                break;
            case 5:
                switch(enTeam.score){
                    case 3:
                        while(!allCandidate.contains(canList.get(r3)) || (r2 < 990 && r3 >= l[Integer.parseInt(role.get(r))]) || canType.get(allCandidate.indexOf(canList.get(r3))) != 3){
                            r3 = (int) (Math.random() * canList.size());
                        }
                        enemyPickCan = canList.get(r3);
                        epcIndex = allCandidate.indexOf(enemyPickCan);
                        break;
                    case 5:
                        while(!allCandidate.contains(canList.get(r3)) || (r2 < 990 && r3 >= l[Integer.parseInt(role.get(r))]) || canType.get(allCandidate.indexOf(canList.get(r3))) != 2){
                            r3 = (int) (Math.random() * canList.size());
                        }
                        enemyPickCan = canList.get(r3);
                        epcIndex = allCandidate.indexOf(enemyPickCan);
                        break;
                    case 6:
                        while(!allCandidate.contains(canList.get(r3)) || (r2 < 990 && r3 >= l[Integer.parseInt(role.get(r))]) || canType.get(allCandidate.indexOf(canList.get(r3))) != 1){
                            r3 = (int) (Math.random() * canList.size());
                        }
                        enemyPickCan = canList.get(r3);
                        epcIndex = allCandidate.indexOf(enemyPickCan);
                        break;
                    default:
                        while(!allCandidate.contains(canList.get(r3)) || (r2 < 990 && r3 >= l[Integer.parseInt(role.get(r))])){
                            r3 = (int) (Math.random() * canList.size());
                        }
                        enemyPickCan = canList.get(r3);
                        epcIndex = allCandidate.indexOf(enemyPickCan);
                        break;
                }
                break;
        }
        roleList.remove(r);
        canType.remove(epcIndex);
        allCandidate.remove(epcIndex);
        enemyOrder += role.get(r);
        role.remove(r);
        return canList.get(r3);
    }
}