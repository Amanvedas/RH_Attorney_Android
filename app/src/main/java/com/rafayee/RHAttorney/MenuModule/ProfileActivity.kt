package com.rafayee.RHAttorney.MenuModule

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RH.AttorneyList.Adapter.AttorneyListAdapter
import com.rafayee.RHAttorney.MenuModule.Adapter.ProfileAdapter
import com.rafayee.RHAttorney.R
import com.skyhope.showmoretextview.ShowMoreTextView


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
       // val accadamicList : RecyclerView = findViewById(R.id.recycleview)
        val textView = findViewById<ShowMoreTextView>(R.id.text_view_show_more)

/*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/

        supportActionBar?.hide()
        val adapter= ProfileAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
       /* accadamicList.layoutManager = linearLayoutManager
        accadamicList.adapter = adapter
*/


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