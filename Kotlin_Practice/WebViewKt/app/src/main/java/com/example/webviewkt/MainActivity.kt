package com.example.webviewkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.settings.javaScriptEnabled = true // 자바스크립트 허용
        /* 웹뷰에서 새 창이 뜨지 않도록 방지하는 구문 */
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        // 필수옵션이라고 생각!
        webView.loadUrl("http://www.naver.com") // 링크 주소를 Load함.
    }

    override fun onBackPressed() { // back button 로직
        if(webView.canGoBack()){ // 웹사이트에서 뒤로 갈 페이지가 존재 한다면?
            webView.goBack() // 웹사이트 뒤로가기
        }
        else{
            super.onBackPressed() // 본래의 백버튼 기능 수행 (안드로이드)
        }
    }
}