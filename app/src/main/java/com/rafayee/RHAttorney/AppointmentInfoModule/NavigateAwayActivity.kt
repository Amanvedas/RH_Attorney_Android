package com.rafayee.RHAttorney.AppointmentInfoModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import com.rafayee.RH.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RH.VideoCall.View.VideoActivity
import com.rafayee.RHAttorney.R

class NavigateAwayActivity : AppCompatActivity() {
    lateinit var handler: Handler

    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigate_away)
        supportActionBar?.hide()

        var myRunnable = Runnable {
            // do something
            var intent : Intent = Intent(this, MeetingEndedActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        handler = Handler()
        handler.postDelayed(myRunnable, 600 * 8)
        progressBar = findViewById(R.id.progressbar)
        progressBar.max = 100
        progressBar.progress = 20
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, HomeWithBottomTabsActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
    }

}