package com.example.textviewkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) { // 앱이 최초 실행됐을 때 수행
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // xml 화면 뷰를 연결한다

        tv_title.setText("Hello World") // 텍스트의 값을 변경한다.
    }
}