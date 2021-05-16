package com.rafayee.RHAttorney.AppointmentInfoModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.rafayee.RHAttorney.R

class NavigateAwayActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigate_away)

        progressBar = findViewById(R.id.progressbar)
        progressBar.max = 100
        progressBar.progress = 20
    }
}