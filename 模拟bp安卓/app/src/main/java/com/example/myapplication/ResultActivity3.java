package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity3 extends AppCompatActivity {
    TextView bc,rc,tvbp1,tvbp2,tvbp3,tvbp4,tvbp5,tvbb1,tvbb2,tvbb3,tvbb4,tvbb5,tvrp1,tvrp2,tvrp3,tvrp4,tvrp5,tvrb1,tvrb2,tvrb3,tvrb4,tvrb5,bt,rt,result;
    Button btnApply15;

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
        setContentView(R.layout.activity_result3);
        Parameters.setContext(this);

        tvbp1 = this.findViewById(R.id.bp1_3);
        tvbp2 = this.findViewById(R.id.bp2_3);
        tvbp3 = this.findViewById(R.id.bp3_3);
        tvbp4 = this.findViewById(R.id.bp4_3);
        tvbp5 = this.findViewById(R.id.bp5_3);
        tvrp1 = this.findViewById(R.id.rp1_3);
        tvrp2 = this.findViewById(R.id.rp2_3);
        tvrp3 = this.findViewById(R.id.rp3_3);
        tvrp4 = this.findViewById(R.id.rp4_3);
        tvrp5 = this.findViewById(R.id.rp5_3);
        tvbb1 = this.findViewById(R.id.bb1_3);
        tvbb2 = this.findViewById(R.id.bb2_3);
        tvbb3 = this.findViewById(R.id.bb3_3);
        tvbb4 = this.findViewById(R.id.bb4_3);
        tvbb5 = this.findViewById(R.id.bb5_3);
        tvrb1 = this.findViewById(R.id.rb1_3);
        tvrb2 = this.findViewById(R.id.rb2_3);
        tvrb3 = this.findViewById(R.id.rb3_3);
        tvrb4 = this.findViewById(R.id.rb4_3);
        tvrb5 = this.findViewById(R.id.rb5_3);
        bc = this.findViewById(R.id.bc_3);
        rc = this.findViewById(R.id.rc_3);
        bt = this.findViewById(R.id.bt_3);
        rt = this.findViewById(R.id.rt_3);
        result = this.findViewById(R.id.result_3);
        btnApply15 = this.findViewById(R.id.buttonApply15);

        tvbb1.setText((String) Parameters.getSelectedCan().get("bb1"));
        tvbb2.setText((String) Parameters.getSelectedCan().get("bb2"));
        tvbb3.setText((String) Parameters.getSelectedCan().get("bb3"));
        tvbb4.setText((String) Parameters.getSelectedCan().get("bb4"));
        tvbb5.setText((String) Parameters.getSelectedCan().get("bb5"));
        tvrb1.setText((String) Parameters.getSelectedCan().get("rb1"));
        tvrb2.setText((String) Parameters.getSelectedCan().get("rb2"));
        tvrb3.setText((String) Parameters.getSelectedCan().get("rb3"));
        tvrb4.setText((String) Parameters.getSelectedCan().get("rb4"));
        tvrb5.setText((String) Parameters.getSelectedCan().get("rb5"));
        int blueNum = 0;
        int redNum = 0;
        switch(Parameters.getEnemySide()){
            case 0:
                blueNum = Parameters.getMyTeam();
                redNum = Parameters.getEnemyTeam();
                bc.setText("coach：" + Parameters.getMyId());
                rc.setText("coach：" + Parameters.getEnemyId());
                break;
            case 1:
                redNum = Parameters.getMyTeam();
                blueNum = Parameters.getEnemyTeam();
                bc.setText("coach：" + Parameters.getEnemyId());
                rc.setText("coach：" + Parameters.getMyId());
                break;
        }
        bt.setText(teamInfo[blueNum][0]);
        rt.setText(teamInfo[redNum][0]);
        tvbp1.setText(teamInfo[blueNum][1] + " " + Parameters.getSelectedCan().get("bp1"));
        tvbp2.setText(teamInfo[blueNum][2] + " " + Parameters.getSelectedCan().get("bp2"));
        tvbp3.setText(teamInfo[blueNum][3] + " " + Parameters.getSelectedCan().get("bp3"));
        tvbp4.setText(teamInfo[blueNum][4] + " " + Parameters.getSelectedCan().get("bp4"));
        tvbp5.setText(teamInfo[blueNum][5] + " " + Parameters.getSelectedCan().get("bp5"));
        tvrp1.setText(teamInfo[redNum][1] + " " + Parameters.getSelectedCan().get("rp1"));
        tvrp2.setText(teamInfo[redNum][2] + " " + Parameters.getSelectedCan().get("rp2"));
        tvrp3.setText(teamInfo[redNum][3] + " " + Parameters.getSelectedCan().get("rp3"));
        tvrp4.setText(teamInfo[redNum][4] + " " + Parameters.getSelectedCan().get("rp4"));
        tvrp5.setText(teamInfo[redNum][5] + " " + Parameters.getSelectedCan().get("rp5"));
        switch(Parameters.getResult()){
            case 1:
                result.setText(teamInfo[Parameters.getMyTeam()][0] + "获胜！你赢了！");
                break;
            case 2:
                result.setText(teamInfo[Parameters.getEnemyTeam()][0] + "获胜！你输了...");
                break;
        }

        btnApply15.setOnClickListener(new ResultActivity3.L15());
    }

    class L15 implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(ResultActivity3.this, ModeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
