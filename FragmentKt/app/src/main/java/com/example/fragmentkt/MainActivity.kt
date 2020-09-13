package com.example.fragmentkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFrag(0)

        btn_fragment1.setOnClickListener{
            setFrag(0)
        }
        btn_fragment2.setOnClickListener{
            setFrag(1)
        }
        btn_fragment3.setOnClickListener{
            setFrag(2)
        }
    }

    private fun setFrag(fragNum:Int) { // Fragment 교체
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum){
            0 -> {
                ft.replace(R.id.main_frame, Fragment1()).commit() // commit 저장
            }
            1 -> {
                ft.replace(R.id.main_frame, Fragment2()).commit() // commit 저장
            }
            2 -> {
                ft.replace(R.id.main_frame, Fragment3()).commit() // commit 저장
            }
        }
    }
}