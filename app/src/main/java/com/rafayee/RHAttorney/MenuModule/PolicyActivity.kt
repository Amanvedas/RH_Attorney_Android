package com.rafayee.RHAttorney.MenuModule

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import com.rafayee.RHAttorney.R


class PolicyActivity : AppCompatActivity() {
    lateinit var imgBack : ImageView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy)
        supportActionBar?.hide()
        val webView : WebView = findViewById(R.id.web_view)

        imgBack = findViewById(R.id.img_back)
        imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        webView.settings.setJavaScriptEnabled(true)
        webView.loadUrl("http://dev-api.robinsonandhenry.com/RH_Core/RobinsonAndHenryPrivacyPolicy.html")
    }
}