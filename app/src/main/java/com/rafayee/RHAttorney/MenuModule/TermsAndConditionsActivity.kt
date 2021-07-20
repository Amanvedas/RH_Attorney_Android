package com.rafayee.RHAttorney.MenuModule

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.MenuModule.Model.CompanyInfo
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection
import com.vedas.apna.ServerConnections.AppStatus

class TermsAndConditionsActivity : AppCompatActivity() , RetrofitCallbacks.ServerResponseInterface{
    lateinit var img_back : ImageView
    lateinit var webView : WebView
    lateinit var txt_modify: TextView
    lateinit var txt_title: TextView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)
        supportActionBar?.hide()
        img_back= findViewById(R.id.img_back)
        webView = findViewById(R.id.webview)
        txt_modify = findViewById(R.id.txt_modify)
        txt_title = findViewById(R.id.txt_title)
        img_back.setOnClickListener { finish() }
        webView.webViewClient = MyBrowser()
        webView.settings.setJavaScriptEnabled(true)
        webView.settings.loadsImagesAutomatically = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.overScrollMode = View.OVER_SCROLL_NEVER
        RetrofitCallbacks.getInstace().initializeServerInterface(this@TermsAndConditionsActivity)

        if (getIntent().getStringExtra("terms").equals("terms")){
            txt_title.setText("Terms of Use")
            fetchTerms("companyInfo/","terms")
        }else if (getIntent().getStringExtra("terms").equals("privacy")){
            txt_title.setText("Privacy Policy")
            fetchTerms("companyInfo/","privacy")
        }else{
            txt_title.setText("End User License Agreement")
            fetchTerms("companyInfo/","end")
        }

    }
    fun fetchTerms(url: String, from: String) {
        Log.e("called", "call")
        if (AppStatus.getInstance(this).isConnected()) {
            ProgressDialog.getInstance().showProgress(this)
            RetrofitCallbacks.getInstace().initializeServerInterface(this@TermsAndConditionsActivity)
            RetrofitCallbacks.getInstace().ApiCallbacksGet(this, url, from)
        } else {
            Toast.makeText(this, "No Internet Connection!!!!", Toast.LENGTH_SHORT).show()
        }
    }

    class MyBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    override fun failureCallBack(failureMsg: String?) {
        ProgressDialog.getInstance().hideProgress()

        Log.e("chekcidnf","dsff"+failureMsg)

    }

    override fun successCallBack(body: String?, from: String?) {
        ProgressDialog.getInstance().hideProgress()
        Log.e("chekcidnf","dsffd")
        if (body != null) {
            Log.e("success", body)
            val gson = Gson()
            val res: CompanyInfo = gson.fromJson(body, CompanyInfo::class.java)
            if (from.equals("terms")){
                webView.loadUrl(ServerApiCollection.IMAGE_URL+res.companyInformation.links.termsOfUseEndPoint)
                txt_modify.setText("Last Modified: "+res.companyInformation.links.termsOfUseLastModified)
            }else if (from.equals("privacy")){
                webView.loadUrl(ServerApiCollection.IMAGE_URL+res.companyInformation.links.privacyPolicyEndPoint)
                txt_modify.setText("Last Modified: "+res.companyInformation.links.privacyPolicyLastModified)
            }else{
                webView.loadUrl(ServerApiCollection.IMAGE_URL+res.companyInformation.links.appLicenceEndPoint)
                txt_modify.setText("Last Modified: "+res.companyInformation.links.appLicenceLastModified)
            }
            ProgressDialog.getInstance().hideProgress()
        }    }
}