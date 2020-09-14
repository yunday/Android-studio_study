package com.example.listviewkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var UserList = arrayListOf<User>(
        User(R.drawable.android, "종윤이", "24", "앵두같은...."),
        User(R.drawable.android, "경윤이", "22", "나 ^^"),
        User(R.drawable.android, "홍이", "22", "홍사운드"),
        User(R.drawable.android, "하연이", "22", "내이름은 가하연"),
        User(R.drawable.android, "지니", "22", "손예진"),
        User(R.drawable.android, "자니", "22", "한가인&&동준"),
        User(R.drawable.android, "경연이", "22", "경연:생각정리중...")

    ) // arrayListOf<자료형>(자료형인 담을 객체)
    override fun onCreate(savedInstanceState: Bundle?) { // 액티비티의 실행 시작 지점
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val item = arrayOf("사과", "배", "딸기", "종윤", "경윤")
//        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, item)
//        // context 는 한 액티비티의 모든 정보를 담고있다.
//        // listView 에서는 adapter 로 연결을 해주어야만 데이터들을 직접 넣을 수 있음

        val Adapter = UserAdapter(this, UserList)
        listView.adapter = Adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener{parent, view, position, id ->
            val selectItem = parent.getItemAtPosition(position) as User
            Toast.makeText(this, selectItem.name, Toast.LENGTH_SHORT).show()


        }

    }
}