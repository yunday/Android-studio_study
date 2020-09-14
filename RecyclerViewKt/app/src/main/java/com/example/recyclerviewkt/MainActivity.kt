package com.example.recyclerviewkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val profileList = arrayListOf(
            Profiles(R.drawable.woman, "김경윤", 22, "부경대학교 학생"),
            Profiles(R.drawable.man, "임종윤", 24, "부경대학교 학생"),
            Profiles(R.drawable.woman, "박가연", 22, "곱창걸스"),
            Profiles(R.drawable.woman, "반유진", 22, "곱창걸스"),
            Profiles(R.drawable.woman, "신유진", 22, "곱창걸스"),
            Profiles(R.drawable.woman, "정경연", 22, "곱창걸스"),
            Profiles(R.drawable.woman, "홍지수", 22, "곱창걸스"),
            Profiles(R.drawable.woman, "이지인", 22, "동국대학교 학생"),
            Profiles(R.drawable.woman, "김소현", 22, "부산대학교 학생"),
            Profiles(R.drawable.woman, "탁은경", 22, "동의대학교 학생"),

        )

        rv_profile.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)// layoutManager 이 리사이클뷰에서 연결을 해줌
        rv_profile.setHasFixedSize(true) // 성능 개선 방안
        rv_profile.adapter =  ProfileAdapter(profileList)// 어댑터 연결
    }
}