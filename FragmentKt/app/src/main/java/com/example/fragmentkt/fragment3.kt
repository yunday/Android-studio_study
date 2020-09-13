package com.example.fragmentkt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class Fragment3 : Fragment(){
    // 프래그먼트 실행시 이게 먼저 시작
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag3, container, false)
        // setContentView(R.layout.activity_main) 랑 같음! xml 파일과 연동시키는 것

        return view
    }
}