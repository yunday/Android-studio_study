package com.example.imageviewkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_toast.setOnClickListener{

            iv_profile.setImageResource(R.drawable.apple) // 이미지 뷰에 새로운 이미지 등록
            Toast.makeText(this@MainActivity, "버튼이 클릭 되었습니다.", Toast.LENGTH_SHORT).show()
            // 액티비티, 띄울 메세지, 얼마나 오래 띄울것인지 .show() 보이게 함

        }
    }
}