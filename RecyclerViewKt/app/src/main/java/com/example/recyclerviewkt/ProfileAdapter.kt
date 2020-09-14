package com.example.recyclerviewkt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ProfileAdapter(val profileList: ArrayList<Profiles>): RecyclerView.Adapter<ProfileAdapter.CustomViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.CustomViewHolder {
        // onCreate 뷰와 비슷함 xml 을 연결함
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view).apply { // view 를 연결시킴
            // view
            itemView.setOnClickListener {
                val curPos: Int = adapterPosition// 현재 클릭한놈의 위치
                val profile: Profiles = profileList.get(curPos)
                Toast.makeText(parent.context, "이름 : ${profile.name}\n 나이 : ${profile.age}\n 직업 : ${profile.job}", Toast.LENGTH_SHORT).show() // parent.context 는 mainactivity를 의미함

            }
        }
    }

    override fun onBindViewHolder(holder: ProfileAdapter.CustomViewHolder, position: Int) {
        // 스크롤할때마다 이 함수가 호출될때마다 뷰를 실제 연결
        holder.gender.setImageResource(profileList.get(position).gender) // 현재 클릭한 위치
        holder.name.text = profileList.get(position).name // 바인드 될때마다 연동시켜줘야해서 포지션마다 분기처리를 해줌
        holder.age.text = profileList.get(position).age.toString() // age 정수형이므로 문자열로 바꾸어주어야함
        holder.job.text = profileList.get(position).job
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) { // 내부클래스
        // 아이디를 뷰를 통해 가져옴
        val gender = itemView.findViewById<ImageView>(R.id.iv_profile) // 성별
        val name = itemView.findViewById<TextView>(R.id.tv_name) // 이름
        val age = itemView.findViewById<TextView>(R.id.tv_age) // 나이
        val job = itemView.findViewById<TextView>(R.id.tv_job) // 직업
    }


}