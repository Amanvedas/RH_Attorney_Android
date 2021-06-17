package com.rafayee.RH.SplashScreen

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import com.rafayee.RH.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RH.Login.View.LoginActivity
import com.rafayee.RHAttorney.Login.LoginResponseController
import com.rafayee.RHAttorney.MainActivity
import com.rafayee.RHAttorney.R

class SplashScreen : AppCompatActivity() {
    var filename = "Valustoringfile"
    var SP: SharedPreferences? = null
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
            SP = getSharedPreferences(filename, 0)
            val getname: String? = SP!!.getString("key3", "")
            Log.e("ididif","d: "+getname)
            if (getname.equals("isLogin")){
                val intent = Intent(this, HomeWithBottomTabsActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 3000) // 3000 is the delayed time in milliseconds.

    }
}