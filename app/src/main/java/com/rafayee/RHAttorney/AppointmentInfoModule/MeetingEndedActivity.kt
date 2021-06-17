package com.rafayee.RHAttorney.AppointmentInfoModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.rafayee.RH.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RH.Login.View.LoginActivity
import com.rafayee.RHAttorney.ConsultationModule.ConsultationResultActivity
import com.rafayee.RHAttorney.R

class MeetingEndedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meeting_ended)
        supportActionBar?.hide()
        var txtSend : TextView = findViewById(R.id.txt_send)
        var txtNon : TextView = findViewById(R.id.txt_non)
        txtSend.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ConsultationResultActivity::class.java).putExtra("send","send").setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
        })
        txtNon.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ConsultationResultActivity::class.java).putExtra("send","non").setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, HomeWithBottomTabsActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
    }
}