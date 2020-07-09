package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button btn_move;
    private EditText et_test;
    private String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_test = findViewById(R.id.et_test);


        btn_move = findViewById(R.id.btn_move);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //화면 전환
                str = et_test.getText().toString();
                Intent intent = new Intent(MainActivity.this , SubActivity.class); // 현재 activity, 이동하고싶은 activity
                intent.putExtra("str", str);    // str문자열 값을 보냄
                // "str" 이거는 자유롭게 써도 되는데 별명임.
                startActivity(intent); //액티비티 이동 구문
            }
        });

    }
}
