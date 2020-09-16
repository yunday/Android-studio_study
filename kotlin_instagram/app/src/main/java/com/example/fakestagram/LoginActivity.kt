package com.example.fakestagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance() // FirebaseAuth 객체 생성
        email_login_button.setOnClickListener {
            signinAndSignup()
        }
    }
    // 로그인 및 회원가입 기능 하는 함수
    fun signinAndSignup(){
        // 인자는 email, password 순서
        // id.text == findViewById(R.id.id) 로 받은 객체.text 한 값과 같음
        auth?.createUserWithEmailAndPassword(email_edittext.text.toString(), password_tedittext.text.toString())
            ?.addOnCompleteListener {
            task ->
                if(task.isSuccessful){
                    // Creating a user account
                    moveMainPage(task.result?.user) // function 호출하는 코드
                }else if(task.exception?.message.isNullOrEmpty()){
                    // Show the error message
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }else{
                    // login if you have account
                    signinEmail()
                }
        }
    }
    fun signinEmail(){
        auth?.signInWithEmailAndPassword(email_edittext.text.toString(), password_tedittext.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    // Login success
                    moveMainPage(task.result?.user)
                }else{
                    // Show the error message
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun moveMainPage(user: FirebaseUser?){// 로그인 성공시 다음 페이지로 넘어감
        if(user != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}