package com.example.listviewkt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class UserAdapter(val context: Context, val UserList: ArrayList<User>) : BaseAdapter(){
    override fun getCount(): Int { // 리스트의 사이즈를 반환함
        return UserList.size
    }

    override fun getItem(position: Int): Any {
        return UserList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    // 리스트 뷰에서 뷰가 뷰를 가지고 왔을때 어떻게 풀어줄거냐?
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item_user, null)
        // LayoutInflater -> 뷰를 붙이는 과정이다

        // User model 대로 차근차근 선언하는 과정
        val profile = view.findViewById<ImageView>(R.id.iv_profile) // 이 아이디로부터 뷰를 찾아라
        val name = view.findViewById<TextView>(R.id.tv_name)
        val age = view.findViewById<TextView>(R.id.tv_age)
        val greet = view.findViewById<TextView>(R.id.tv_greet)

        val user = UserList[position] // 배열의 순서 (0 부터 셈) (User 객체의 배열)

        profile.setImageResource(user.profile)
        name.text = user.name
        age.text = user.age
        greet.text = user.greet

        return view
    }
}