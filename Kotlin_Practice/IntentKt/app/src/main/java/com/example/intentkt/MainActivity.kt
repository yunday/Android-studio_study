package com.example.intentkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sub.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_a.setOnClickListener{
            val intent = Intent(this, SubActivity::class.java) // 다른 화면으로 이동하기 위한 인텐트 객체 생성.
            intent.putExtra("msg", tv_id.text.toString()) // intent라는 객체에 값을 꾸러미에 담아 넘길 수 있음 // HellooWorld라는 텍스트 값을 담은 뒤 msg라는 키로 잠궜다.
            startActivity(intent) // intent에 저장되어있는 액티비티 쪽으로 이동한다.
            finish() // 자기 자신 액티비티를 파괴한다
        }
    }
}