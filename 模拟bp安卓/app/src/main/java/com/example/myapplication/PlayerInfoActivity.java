package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class PlayerInfoActivity extends AppCompatActivity {

    Button tb,sb,btnApply12;
    EditText et;

    final String[] team = {"SKT","DWG","EDG","RYL","ROX","IG","SSG","DGL"};
    final String[] side = {"红色方","蓝色方"};
    int yt = -1;
    int ys = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);
        Parameters.setContext(this);

        et = this.findViewById(R.id.playerIdInput3);
        tb = this.findViewById(R.id.playerTeamBtn3);
        sb = this.findViewById(R.id.sideBtn3);
        btnApply12 = this.findViewById(R.id.buttonApply12);

        tb.setOnClickListener(new PlayerInfoActivity.LT());
        sb.setOnClickListener(new PlayerInfoActivity.LS());
        btnApply12.setOnClickListener(new PlayerInfoActivity.L12());
    }

    class LT implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(PlayerInfoActivity.this, AlertDialog.THEME_HOLO_DARK);
            builder1.setTitle("选择队伍").setSingleChoiceItems(team, yt, (dialog, which) -> {yt = which;tb.setText(team[which]);}).setPositiveButton("确定", (dialog, which) -> {});
            AlertDialog alertDialog1 = builder1.setCancelable(false).create();
            alertDialog1.show();
        }
    }

    class LS implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(PlayerInfoActivity.this, AlertDialog.THEME_HOLO_DARK);
            builder2.setTitle("选择位置").setSingleChoiceItems(side, ys, (dialog, which) -> {ys = which;sb.setText(side[which]);}).setPositiveButton("确定", (dialog, which) -> {});
            AlertDialog alertDialog2 = builder2.setCancelable(false).create();
            int es = Parameters.getEnemySide();
            if(es != -1){
                alertDialog2.setOnShowListener(dialog -> alertDialog2.getListView().getChildAt(es).setEnabled(false));
            }
            alertDialog2.show();
        }
    }

    class L12 implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            if(et.getText().toString().isEmpty() || et.getText().toString().equals("")){
                new AlertDialog.Builder(PlayerInfoActivity.this)
                        .setTitle("提示")
                        .setMessage("id不能为空！")
                        .setCancelable(true)
                        .setPositiveButton("确定", null)
                        .show();
            } else if(ys == -1){
                new AlertDialog.Builder(PlayerInfoActivity.this)
                        .setTitle("提示")
                        .setMessage("请选择你的位置！")
                        .setCancelable(true)
                        .setPositiveButton("确定", null)
                        .show();
            } else {
                Parameters.setSideValid(0);
                if(yt == -1){
                    yt = (int) (Math.random() * 8);
                }
                JSONObject jo = new JSONObject();
                SBPSocket s = Parameters.getSession();
                jo.put("phase", 1);
                jo.put("id", et.getText().toString());
                jo.put("team", yt);
                jo.put("side", ys);
                s.send(JSON.toJSONString(jo));
                btnApply12.setText("...");
                while(Parameters.getSideValid() == 0){
                    SystemClock.sleep(300);
                }
                btnApply12.setText("确定");
                if(Parameters.getSideValid() == 1){
                    Parameters.setMyId(et.getText().toString());
                    Parameters.setMyTeam(yt);
                    Parameters.setEnemySide(1 - ys);
                    Intent intent = new Intent(PlayerInfoActivity.this, MainActivity3.class);
                    startActivity(intent);
                    finish();
                }
                if(Parameters.getSideValid() == 2){
                    new AlertDialog.Builder(PlayerInfoActivity.this)
                            .setTitle("提示")
                            .setMessage("对方已选择此位置，请重新选择。")
                            .setCancelable(true)
                            .setPositiveButton("确定", null)
                            .show();
                    Parameters.setSideValid(0);
                }
            }
        }
    }
}
