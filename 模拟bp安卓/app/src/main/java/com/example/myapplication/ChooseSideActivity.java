package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseSideActivity extends AppCompatActivity {
    Button btnBlue,btnRed,btnApply2;
    String side;

    String teamNum, enemyNum;

    String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_side);

        btnBlue = this.findViewById(R.id.buttonBlue);
        btnBlue.setOnClickListener(new ChooseSideActivity.LBlue());
        btnRed = this.findViewById(R.id.buttonRed);
        btnRed.setOnClickListener(new ChooseSideActivity.LRed());

        btnApply2 = this.findViewById(R.id.buttonApply2);
        btnApply2.setOnClickListener(new ChooseSideActivity.L2());

        teamNum = getIntent().getStringExtra("teamNum");
        enemyNum = getIntent().getStringExtra("enemyNum");
        side = "blue";

        num = getIntent().getStringExtra("num");
    }

    class LBlue implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            side = "blue";
        }
    }

    class LRed implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            side = "red";
        }
    }

    class L2 implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(ChooseSideActivity.this, MainActivity.class);
            intent.putExtra("teamNum", teamNum);
            intent.putExtra("enemyNum", enemyNum);
            intent.putExtra("side", side);
            intent.putExtra("num", num);
            startActivity(intent);
            finish();
        }
    }
}
