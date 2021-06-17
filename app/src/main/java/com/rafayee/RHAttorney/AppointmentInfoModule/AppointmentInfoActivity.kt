package com.rafayee.RHAttorney.AppointmentInfoModule

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rafayee.RH.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RH.MenuModule.View.UpdatePasswordActivity
import com.rafayee.RHAttorney.Login.LoginResponseController
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection

class AppointmentInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_info)
        supportActionBar?.hide()
        val btnSubmit : Button = findViewById(R.id.btn_join_meeting)
        val btnAttend : Button = findViewById(R.id.btn_attend)
        val imgBack : ImageView = findViewById(R.id.back_btn)
        val imgShare : ImageView = findViewById(R.id.img_share)
        val txtShare : TextView = findViewById(R.id.txt_share)
        val circleImage : ImageView = findViewById(R.id.profileIcon)

        imgShare.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, InviteOtherPeopleActivity::class.java))

        })
        Glide.with(this)
            .load(ServerApiCollection.IMAGE_URL+ LoginResponseController.myObj?.loginResponseModel!!.clientInfo!!.profilePic)
            .placeholder(R.drawable.profile_ic)
            .into(circleImage)

        txtShare.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, InviteOtherPeopleActivity::class.java))
        })
        btnSubmit.setOnClickListener(View.OnClickListener {
            btnAttend.visibility = View.VISIBLE
        })
        imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

/*
        btnAttend.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, InviteOtherPeopleActivity::class.java))
        })
*/

    }
/*
    override fun onBackPressed() {
        startActivity(Intent(this, HomeWithBottomTabsActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
        super.onBackPressed()

    }
*/

}