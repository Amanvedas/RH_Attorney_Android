package com.rafayee.RHAttorney.ConsultationModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.rafayee.RHAttorney.R

class SendFreeAgreementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_free_agreement)
        supportActionBar?.hide()
        var imgBack : ImageView = findViewById(R.id.back_img)
        imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

    }
}