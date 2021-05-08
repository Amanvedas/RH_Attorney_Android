package com.rafayee.RH.MenuModule

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.rafayee.RHAttorney.R

class TermsAndConditionsActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)
        supportActionBar?.hide()
        val webView : WebView = findViewById(R.id.webview)
        webView.settings.setJavaScriptEnabled(true)
        webView.loadUrl("https://www.robinsonandhenry.com/policy-terms-conditions/")
    }
}