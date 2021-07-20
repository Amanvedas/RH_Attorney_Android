package com.rafayee.RHAttorney.ConsultationModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rafayee.RHAttorney.R

class ConsultationPaymentActivity : AppCompatActivity() {

    lateinit var  txtOne : TextView
    lateinit var  txtTwo : TextView
    lateinit var  txtTree : TextView
    lateinit var  txtFour : TextView
    lateinit var  txtfive : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultation_payment)
        supportActionBar?.hide()
        txtOne = findViewById(R.id.txt_one)
        txtTwo  = findViewById(R.id.txt_2)
        txtTree  = findViewById(R.id.txt_3)
        txtFour = findViewById(R.id.noLongerNeededText)
        txtfive  = findViewById(R.id.unableAttendText)

        val backImg : ImageView = findViewById(R.id.img_back)
        val txtOne : TextView = findViewById(R.id.txt_one)
        backImg.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        txtOne.setOnClickListener(View.OnClickListener {
            changeButton()
            txtOne.setBackgroundResource(R.drawable.blue_round_boarder);

            startActivity(Intent(this, ConsultationReviewActivity::class.java))

        })
        txtTwo.setOnClickListener(View.OnClickListener {
            changeButton()
            txtTwo.setBackgroundResource(R.drawable.blue_round_boarder);

        })
        txtTree.setOnClickListener(View.OnClickListener {
            changeButton()
            txtTree.setBackgroundResource(R.drawable.blue_round_boarder);

        })

        txtFour.setOnClickListener(View.OnClickListener {
            changeButton()
            txtFour.setBackgroundResource(R.drawable.blue_round_boarder);

        })
        txtfive.setOnClickListener(View.OnClickListener {
            changeButton()
            txtfive.setBackgroundResource(R.drawable.blue_round_boarder);

        })

    }
    fun changeButton(){
        txtOne.setBackgroundResource(R.drawable.cancel_options_boarder);
        txtTwo.setBackgroundResource(R.drawable.cancel_options_boarder);
        txtTree.setBackgroundResource(R.drawable.cancel_options_boarder);
        txtFour.setBackgroundResource(R.drawable.cancel_options_boarder);
        txtfive.setBackgroundResource(R.drawable.cancel_options_boarder);
    }

}