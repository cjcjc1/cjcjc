package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseTeamActivity extends AppCompatActivity {
    Button btnSKT,btnDWG,btnEDG,btnRYL,btnROX,btnIG,btnSSG,btnLGD,btnApply;
    int teamNum = 0;
    int enemyNum;

    String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_team);

        btnSKT = this.findViewById(R.id.buttonSKT);
        btnSKT.setOnClickListener(new LSKT());
        btnDWG = this.findViewById(R.id.buttonDWG);
        btnDWG.setOnClickListener(new LDWG());
        btnEDG = this.findViewById(R.id.buttonEDG);
        btnEDG.setOnClickListener(new LEDG());
        btnRYL = this.findViewById(R.id.buttonRYL);
        btnRYL.setOnClickListener(new LRYL());
        btnROX = this.findViewById(R.id.buttonROX);
        btnROX.setOnClickListener(new LROX());
        btnIG = this.findViewById(R.id.buttonIG);
        btnIG.setOnClickListener(new LIG());
        btnSSG = this.findViewById(R.id.buttonSSG);
        btnSSG.setOnClickListener(new LSSG());
        btnLGD = this.findViewById(R.id.buttonLGD);
        btnLGD.setOnClickListener(new LLGD());

        btnApply = this.findViewById(R.id.buttonApply);
        btnApply.setOnClickListener(new L1());

        if(getIntent().getStringExtra("num") == null){
            num = "1";
        }
        else{
            num = getIntent().getStringExtra("num");
        }
    }

    class LSKT implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            teamNum = 0;
        }
    }

    class LDWG implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            teamNum = 1;
        }
    }

    class LEDG implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            teamNum = 2;
        }
    }

    class LRYL implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            teamNum = 3;
        }
    }

    class LROX implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            teamNum = 4;
        }
    }

    class LIG implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            teamNum = 5;
        }
    }

    class LSSG implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            teamNum = 6;
        }
    }

    class LLGD implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            teamNum = 7;
        }
    }

    class L1 implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            do {
                enemyNum = (int) (Math.random() * 8);
            }
            while(enemyNum == teamNum);

            Intent intent = new Intent(ChooseTeamActivity.this, ChooseSideActivity.class);
            intent.putExtra("teamNum", Integer.toString(teamNum));
            intent.putExtra("enemyNum", Integer.toString(enemyNum));
            intent.putExtra("num", num);
            startActivity(intent);
            finish();
        }
    }
}