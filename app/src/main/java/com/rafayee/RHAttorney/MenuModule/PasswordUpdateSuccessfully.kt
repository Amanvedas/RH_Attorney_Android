package com.rafayee.RH.MenuModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.rafayee.RH.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RHAttorney.AppointmentInfoModule.MeetingEndedActivity
import com.rafayee.RHAttorney.R


class PasswordUpdateSuccessfully : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_update_succussfully)
        supportActionBar?.hide()
        Handler().postDelayed({
            var intent : Intent = Intent(this, HomeWithBottomTabsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }, 1500) // 1000 is the delayed time in milliseconds.
    }
}