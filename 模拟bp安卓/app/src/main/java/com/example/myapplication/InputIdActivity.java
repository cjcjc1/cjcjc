package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class InputIdActivity extends AppCompatActivity {
    Button btnApply6;
    EditText et1,et2;
    ImageButton imgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_id);

        et1 = this.findViewById(R.id.blueIdInput);
        et2 = this.findViewById(R.id.redIdInput);
        btnApply6 = this.findViewById(R.id.buttonApply6);
        imgbtn = this.findViewById(R.id.imageButton);

        btnApply6.setOnClickListener(new InputIdActivity.L6());
        imgbtn.setOnClickListener(new InputIdActivity.Ltip());
    }

    class L6 implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(InputIdActivity.this, ChooseTeamActivity2.class);
            intent.putExtra("p2Id", et2.getText().toString());
            intent.putExtra("p1Id", et1.getText().toString());

            startActivity(intent);
            finish();
        }
    }

    class Ltip implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            new AlertDialog.Builder(InputIdActivity.this)
                    .setTitle("提示")
                    .setMessage("玩家1偶数场次在蓝色方，奇数场次在红色方；玩家2偶数场次在红色方，奇数场次在蓝色方。")
                    .setCancelable(true)
                    .setPositiveButton("确定", null)
                    .show();
        }
    }
}
