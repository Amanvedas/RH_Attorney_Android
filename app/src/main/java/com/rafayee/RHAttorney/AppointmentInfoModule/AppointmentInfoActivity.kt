package com.rafayee.RHAttorney.AppointmentInfoModule

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.rafayee.RHAttorney.AppointmentInfoModule.AppointmeniInfo.AppointmentInfoPresenter
import com.rafayee.RHAttorney.Forgot.Presenter.ForgotPresenter

import com.rafayee.RHAttorney.Login.Model.LoginResponseModel
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONException
import org.json.JSONObject

class AppointmentInfoActivity : AppCompatActivity(){
    lateinit var  loginResponseModel: LoginResponseModel

    lateinit  var img_profile: CircleImageView
    lateinit var txt_name:TextView
    lateinit var txt_job:TextView
    lateinit var txt_disc:TextView
    lateinit var txt_estimate:TextView
    lateinit var txt_time:TextView
    lateinit var txt_case_disc:TextView
    lateinit var txt_hope_disc:TextView
    lateinit var presenter: AppointmentInfoPresenter
    fun initVar(){
        img_profile = findViewById(R.id.img_profile)
        txt_name = findViewById(R.id.txt_name)
        txt_job = findViewById(R.id.txt_job)
        txt_disc = findViewById(R.id.txt_disc)
        txt_estimate = findViewById(R.id.txt_estimate)
        txt_time = findViewById(R.id.txt_time)
        txt_case_disc = findViewById(R.id.txt_case_disc)
        txt_hope_disc = findViewById(R.id.txt_hope_disc)
    }

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
        initVar()
        presenter= AppointmentInfoPresenter()
        presenter.AppointmentInfoPresenter(this, img_profile,
            txt_name,
            txt_job,
            txt_disc,
            txt_estimate,
            txt_time,
            txt_case_disc,
            txt_hope_disc,
        )
        RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
        val pref = getSharedPreferences("LoginPref", 0)
        Log.e("emaiLID","=="+ pref.getString("emailID","").toString())

        presenter.appointmentInfoApi(this,pref.getString("emailID","").toString())
        imgShare.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, InviteOtherPeopleActivity::class.java))

        })
        loginResponseModel = Gson().fromJson(getSharedPreferences("LoginPref", 0).getString("userInfo", ""),
            object : TypeToken<LoginResponseModel>(){}.type)
        Glide.with(this)
            .load(ServerApiCollection.IMAGE_URL+ loginResponseModel.attorneysList.get(0).profilePic)
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


    }


}