package com.rafayee.RH.VideoCall.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rafayee.RH.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RH.Login.View.LoginActivity
import com.rafayee.RHAttorney.AppointmentInfoModule.InviteOtherPeopleActivity
import com.rafayee.RHAttorney.AppointmentInfoModule.MeetingEndedActivity
import com.rafayee.RHAttorney.R
import pl.bclogic.pulsator4droid.library.PulsatorLayout

class CallConnectActivity:AppCompatActivity() {
    lateinit var pulsator: PulsatorLayout
    lateinit var animBlink: Animation
    lateinit var connect: TextView
    lateinit var img_back :ImageView
    lateinit var handler:Handler
    lateinit var progressBar: ProgressBar
    var myRunnable = Runnable {
        // do something
        startActivity(Intent(this, VideoActivity::class.java))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_connect)
        supportActionBar?.hide()
        pulsator = findViewById(R.id.pulsator)
        connect = findViewById(R.id.connect)
        img_back = findViewById(R.id.img_back)
        pulsator.start()
        progressBar = findViewById(R.id.progressBar)
        progressBar.max = 100
        progressBar.progress = 20
        animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
      //  connect.startAnimation(animBlink)
        img_back.setOnClickListener { onBackPressed() }
        handler = Handler()
        handler.postDelayed(myRunnable, 1000 * 8) // 1000 is the delayed time in milliseconds.
    }
    override fun onDestroy() {
        super.onDestroy()
        pulsator.stop()
        connect.clearAnimation()
    }

    override fun onBackPressed() {
        handler.removeCallbacks(myRunnable)
        var intent : Intent = Intent(this, HomeWithBottomTabsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }
}