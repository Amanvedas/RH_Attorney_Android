package com.rafayee.RH.MenuModule

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import com.rafayee.RHAttorney.R

class TermsAndConditionsActivity : AppCompatActivity() {
    lateinit var imgBack : ImageView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)
        supportActionBar?.hide()
        imgBack = findViewById(R.id.img_back)
        imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        val webView : WebView = findViewById(R.id.webview)
        webView.settings.setJavaScriptEnabled(true)
        webView.loadUrl("http://dev-api.robinsonandhenry.com/RH_Core/RobinsonAndHenryTermsOfUse.html")
    }
}