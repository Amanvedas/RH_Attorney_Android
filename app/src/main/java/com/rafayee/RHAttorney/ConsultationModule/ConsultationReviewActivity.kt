package com.rafayee.RHAttorney.ConsultationModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rafayee.RHAttorney.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RHAttorney.R

class ConsultationReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultation_review)
        supportActionBar?.hide()
        val backImg : ImageView = findViewById(R.id.img_back)
        val txtYes : TextView = findViewById(R.id.txt_yes)
        txtYes.setOnClickListener(View.OnClickListener {
            var intent : Intent = Intent(this, HomeWithBottomTabsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        })
        backImg.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

    }
}