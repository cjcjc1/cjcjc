package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseTeamActivity2 extends AppCompatActivity {

    TextView chooseteamtv2,tvb,tvr;
    Button btn,btnb,btnr;

    String p1Id,p2Id;
    int bt = -1, rt = -1;
    int num;
    String p1Score,p2Score;

    final String[] team = {"SKT","DWG","EDG","RYL","ROX","IG","SSG","DGL"};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_team2);

        tvb = this.findViewById(R.id.bluetv);
        tvr = this.findViewById(R.id.redtv);
        btn = this.findViewById(R.id.buttonApply7);
        btnb = this.findViewById(R.id.btnblue);
        btnr = this.findViewById(R.id.btnred);
        chooseteamtv2 = this.findViewById(R.id.chooseteamtv2);

        p1Id = getIntent().getStringExtra("p1Id");
        p2Id = getIntent().getStringExtra("p2Id");

        tvb.setText(p1Id);
        tvr.setText(p2Id);

        if(getIntent().getStringExtra("p1Score") == null){
            p1Score = "0";
            p2Score = "0";
        }
        else{
            p1Score = getIntent().getStringExtra("p1Score");
            p2Score = getIntent().getStringExtra("p2Score");
        }
        num = Integer.parseInt(p1Score) + Integer.parseInt(p2Score) + 1;
        chooseteamtv2.setText("Game" + num + "\n" + p1Id + " " + p1Score + "-" + p2Score + " " + p2Id + "\n\n" + "请选择队伍");

        btn.setOnClickListener(new ChooseTeamActivity2.L7());
        btnb.setOnClickListener(new ChooseTeamActivity2.LB());
        btnr.setOnClickListener(new ChooseTeamActivity2.LR());
    }

    class L7 implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            if(rt == -1){
                do{
                    rt = (int) (Math.random() * 8);
                }while(rt == bt);
            }
            if(bt == -1){
                do{
                    bt = (int) (Math.random() * 8);
                }while(bt == rt);
            }
            Intent intent = new Intent(ChooseTeamActivity2.this, MainActivity2.class);
            intent.putExtra("p1Id", p1Id);
            intent.putExtra("p2Id", p2Id);
            intent.putExtra("p2Num", Integer.toString(rt));
            intent.putExtra("p1Num", Integer.toString(bt));
            intent.putExtra("p1Score", p1Score);
            intent.putExtra("p2Score", p2Score);

            startActivity(intent);
            finish();
        }
    }

    class LR implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(ChooseTeamActivity2.this, AlertDialog.THEME_HOLO_DARK);
            builder1.setTitle("选择队伍").setSingleChoiceItems(team, rt, (dialog, which) -> {rt = which;btnr.setText(team[which]);}).setPositiveButton("确定", (dialog, which) -> {});
            AlertDialog alertDialog1 = builder1.setCancelable(false).create();
            if(bt != -1){
                alertDialog1.setOnShowListener(dialog -> alertDialog1.getListView().getChildAt(bt).setEnabled(false));
            }
            alertDialog1.show();
        }
    }

    class LB implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(ChooseTeamActivity2.this, AlertDialog.THEME_HOLO_DARK);
            builder2.setTitle("选择队伍").setSingleChoiceItems(team, bt, (dialog, which) -> {bt = which;btnb.setText(team[which]);}).setPositiveButton("确定", (dialogInterface, i) -> {});
            AlertDialog alertDialog2 = builder2.setCancelable(false).create();
            if(rt != -1){
                alertDialog2.setOnShowListener(dialog -> alertDialog2.getListView().getChildAt(rt).setEnabled(false));
            }
            alertDialog2.show();
        }
    }
}
