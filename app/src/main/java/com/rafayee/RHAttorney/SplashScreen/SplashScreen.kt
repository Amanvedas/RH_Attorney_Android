package com.rafayee.RHAttorney.SplashScreen

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.rafayee.RHAttorney.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RHAttorney.Login.View.LoginActivity
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.SessionManager
import com.rafayee.RHAttorney.ServerConnections.SessionManager.KEY_USERID
import com.rafayee.RHAttorney.Utils.BiometricUtils.splash_fingerface

class SplashScreen : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)
        supportActionBar?.hide()

        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            redirect()
        }, 3000) // 3000 is the delayed time in milliseconds.

    }
    public fun redirect() {
        val sessionManager = SessionManager(this@SplashScreen)
        Log.e("checkLogin", " " + sessionManager.isLoggedIn)
        if (sessionManager.isLoggedIn) {
            sharedPreferences = getSharedPreferences("RH", MODE_PRIVATE)
            val userID: String? = sharedPreferences.getString(KEY_USERID, "")
            Log.e("userId", " $userID")
            splash_fingerface = true
            startActivity(Intent(this@SplashScreen, HomeWithBottomTabsActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
            //startActivity(Intent(this@SplashScreen, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }else {
            startActivity(Intent(this@SplashScreen, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }
        finish()
    }
}