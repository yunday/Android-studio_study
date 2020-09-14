package com.example.edittextbuttonkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { // 액티비티가 최초 실행 되면 이곳을 수행한다.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_getText.setOnClickListener { // edit 텍스트에 입력되어있는 값을 가지고와서 텍스트뷰에 뿌려준다.
            var resultText = et_id.text.toString() // edit 텍스트에 입력되어있는 값을 문자열형태로 가져와달라
            tv_result.setText(resultText) // 입력된 값을 텍스트뷰에 set 해줌.
        }
    }
}