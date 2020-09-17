package com.example.fakestagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    var googleSignInClient : GoogleSignInClient? = null
    var GOOGLE_LOGIN_CODE = 9001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance() // FirebaseAuth 객체 생성
        email_login_button.setOnClickListener {
            signinAndSignup()
        }
        google_sign_in_button.setOnClickListener {
            // 구글 로그인의 first step
            googleLogin()
        }
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // 147474940771-ahkjocpilc56ubnp8hdrserh3kucue9d.apps.googleusercontent.com 안되면 이거 넣기
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
    fun googleLogin(){
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_LOGIN_CODE){
            // 로그인 결과 값 받아옴
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result != null) {
                if(result.isSuccess){
                    // firebase 에 넘길 수 있도록 함
                    var account = result.signInAccount
                    // 구글 로그인 Second step
                    firebaseAuthWithGoogle(account)
                }
            }
        }
    }

    fun firebaseAuthWithGoogle(account : GoogleSignInAccount?){
        var credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth?.signInWithCredential(credential)
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