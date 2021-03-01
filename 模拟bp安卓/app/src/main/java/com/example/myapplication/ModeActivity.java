package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URI;

public class ModeActivity extends AppCompatActivity {

    Button pvp,pvc,pvp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parameters.init();
        if(!LOLCandidate.isInit()){
            LOLCandidate.init();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        Parameters.setContext(this);

        pvp = this.findViewById(R.id.pvp);
        pvc = this.findViewById(R.id.pvc);
        pvp1 = this.findViewById(R.id.pvp1);

        pvp.setOnClickListener(new ModeActivity.L1());
        pvc.setOnClickListener(new ModeActivity.L2());
        pvp1.setOnClickListener(new ModeActivity.L3());
    }

    class L1 implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(ModeActivity.this, InputIdActivity.class);
            startActivity(intent);
            finish();
        }
    }

    class L2 implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(ModeActivity.this, ChooseTeamActivity.class);
            startActivity(intent);
            finish();
        }
    }

    class L3 implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            //URI uri = URI.create("ws://10.0.2.2:8080/ws/sbp");
            URI uri = URI.create("ws://82.156.87.36:8080/ws/sbp");
            SBPSocket client = new SBPSocket(uri);
            try {
                client.connectBlocking();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Parameters.setSession(client);
            pvp1.setText("...");
            pvp1.setEnabled(false);
            pvp.setEnabled(false);
            pvc.setEnabled(false);
            while(Parameters.getStartStatus() == 0){
                SystemClock.sleep(300);
            }
            pvp1.setText("联机");
            pvp1.setEnabled(true);
            pvp.setEnabled(true);
            pvc.setEnabled(true);
            if(Parameters.getStartStatus() == 1){
                Intent intent = new Intent(ModeActivity.this, PlayerInfoActivity.class);
                startActivity(intent);
                finish();
            }
            if(Parameters.getStartStatus() == 2){
                new AlertDialog.Builder(ModeActivity.this)
                        .setTitle("提示")
                        .setMessage("人数已满，请稍等...")
                        .setCancelable(true)
                        .setPositiveButton("确定", null)
                        .show();
                Parameters.setStartStatus(0);
            }
        }
    }
}
