package com.example.fakestagram

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    var googleSignInClient : GoogleSignInClient? = null
    var GOOGLE_LOGIN_CODE = 9001
    var callbackManager: CallbackManager?=null // facebook 로그인 결과를 가져오는 변수
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
        facebook_login_button.setOnClickListener {
            // 페이스북 로그인의 first step
            facebookLogin()
        }
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // 147474940771-ahkjocpilc56ubnp8hdrserh3kucue9d.apps.googleusercontent.com 안되면 이거 넣기
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // printHashKey()
        callbackManager = CallbackManager.Factory.create()
    }
    // K0FDxv85aZrXXYm0bggrPu9FeOc= 이게 해시 키

    fun printHashKey() { // 해쉬 값 가져오는 함수
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey: String = String(Base64.encode(md.digest(), 0))
                Log.i("TAG", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("TAG", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("TAG", "printHashKey()", e)
        }
    }

    fun googleLogin(){
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    fun facebookLogin(){
        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))

        LoginManager.getInstance()
            .registerCallback(callbackManager, object:FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                    // Second step
                    handleFacebookAccessToken(result?.accessToken)
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException?) {

                }

            })
    }

    fun handleFacebookAccessToken(token : AccessToken?){
        var credential = FacebookAuthProvider.getCredential(token?.token!!)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task -> // firebaseAuthWithGoogle 랑 같으므로 복붙
                if(task.isSuccessful){
                    // Third step
                    // Login success
                    moveMainPage(task.result?.user)
                }else{
                    // Show the error message
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
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

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?){
        var credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task ->
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
        auth?.createUserWithEmailAndPassword(
            email_edittext.text.toString(),
            password_tedittext.text.toString()
        )
            ?.addOnCompleteListener { task ->
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
        auth?.signInWithEmailAndPassword(
            email_edittext.text.toString(),
            password_tedittext.text.toString()
        )
            ?.addOnCompleteListener { task ->
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