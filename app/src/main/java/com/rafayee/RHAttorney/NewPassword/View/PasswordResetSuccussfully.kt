package com.rafayee.RH.NewPassword.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.rafayee.RH.Login.View.LoginActivity
import com.rafayee.RHAttorney.R

class PasswordResetSuccussfully : AppCompatActivity() {

    lateinit var loginText:TextView

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset_succussfully)
        supportActionBar?.hide()
        loginText=findViewById(R.id.loginText)

        loginText.setOnClickListener {
            startActivity(Intent(this@PasswordResetSuccussfully,LoginActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

    }
}