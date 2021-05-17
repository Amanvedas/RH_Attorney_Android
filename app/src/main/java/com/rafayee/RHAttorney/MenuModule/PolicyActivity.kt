package com.rafayee.RH.MenuModule

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.rafayee.RHAttorney.R


class PolicyActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy)
        supportActionBar?.hide()
        val webView : WebView = findViewById(R.id.web_view)
        webView.settings.setJavaScriptEnabled(true)
        webView.loadUrl("http://dev-api.robinsonandhenry.com/RH_Core/RobinsonAndHenryPrivacyPolicy.html")
    }
}