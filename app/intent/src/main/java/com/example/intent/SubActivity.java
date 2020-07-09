package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    private TextView tv_sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        tv_sub = findViewById(R.id.tv_sub);

        Intent intent = getIntent();    // 이쪽으로 날아오는 값이 있으면 여기서 받겠다
        String str = intent.getStringExtra("str");   // 쏘는 곳과 동일하게 별명 맞춰줘야함


        tv_sub.setText(str);

    }
}