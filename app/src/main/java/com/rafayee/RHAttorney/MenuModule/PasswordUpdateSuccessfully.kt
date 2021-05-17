package com.rafayee.RH.MenuModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.rafayee.RHAttorney.R


class PasswordUpdateSuccessfully : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_update_succussfully)
        supportActionBar?.hide()
        Handler().postDelayed({
           /* val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)*/
            finish()
        }, 1000) // 1000 is the delayed time in milliseconds.
    }
}