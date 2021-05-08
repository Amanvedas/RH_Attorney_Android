package com.rafayee.RHAttorney.MenuModule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rafayee.RHAttorney.R
import com.skyhope.showmoretextview.ShowMoreTextView


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val textView = findViewById<ShowMoreTextView>(R.id.text_view_show_more)

        //You have to use following one of method

        // For using character length

        //You have to use following one of method

        // For using character length
       // textView.setShowingChar(numberOfCharacter)
        //number of line you want to short
        //number of line you want to short
        textView.setShowingLine(6)
    }
}