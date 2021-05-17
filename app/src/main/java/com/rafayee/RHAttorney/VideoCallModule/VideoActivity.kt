package com.rafayee.RH.VideoCall.View

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rafayee.RHAttorney.MainActivity
import com.rafayee.RHAttorney.R

class VideoActivity: AppCompatActivity() {
    lateinit var connectActionFab: FloatingActionButton
    private var switchCameraActionFab: FloatingActionButton? = null
    private var localVideoActionFab: FloatingActionButton? = null
    private var muteActionFab: FloatingActionButton? = null
    //private var reconnectingProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        supportActionBar?.hide()
        //reconnectingProgressBar = findViewById(R.id.reconnecting_progress_bar)
        connectActionFab = findViewById(R.id.connect_action_fab)
        switchCameraActionFab = findViewById(R.id.switch_camera_action_fab)
        localVideoActionFab = findViewById(R.id.local_video_action_fab)
        muteActionFab = findViewById(R.id.mute_action_fab)

        connectActionFab.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
    }

    override fun onBackPressed() {

    }
}