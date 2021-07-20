package com.rafayee.RHAttorney.ConsultationModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.rafayee.RHAttorney.R

class ConsultationNotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultation_notes)
        supportActionBar?.hide()
        var btnSave : Button = findViewById(R.id.save_btn)
        val imgBack : ImageView = findViewById(R.id.back)
        imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        btnSave.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ConsultationPaymentActivity::class.java))

        })

    }
}