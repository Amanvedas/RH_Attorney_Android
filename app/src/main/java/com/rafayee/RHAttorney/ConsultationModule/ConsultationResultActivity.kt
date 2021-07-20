package com.rafayee.RHAttorney.ConsultationModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.rafayee.RHAttorney.AppointmentInfoModule.MeetingEndedActivity
import com.rafayee.RHAttorney.R
import org.w3c.dom.Text

class ConsultationResultActivity : AppCompatActivity() {
   lateinit var  txtOne : TextView
    lateinit var  txtTwo : TextView
    lateinit var  txtTree : TextView
    lateinit var  txtFour : TextView
    lateinit var  txtfive : TextView
    lateinit var  txtSix : TextView
    lateinit var  txtSeven : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_consultation_result)
        var btnSave : TextView = findViewById(R.id.btn_save)
        var  txtDisc :TextView = findViewById(R.id.txt_disc)
        var imgBack : ImageView = findViewById(R.id.img_back)
         txtOne = findViewById(R.id.txt_1)
         txtTwo  = findViewById(R.id.txt_2)
         txtTree  = findViewById(R.id.txt_3)
         txtFour = findViewById(R.id.noLongerNeededText)
         txtfive  = findViewById(R.id.unableAttendText)
         txtSix  = findViewById(R.id.hiredAnotherAttorneyText)
         txtSeven = findViewById(R.id.otherText)

        val bundle: Bundle? = intent.extras
        val string: String? = intent.getStringExtra("send")
        val edtNote : EditText = findViewById(R.id.editText)
        imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        if (string.equals("send")){
            txtDisc.text = "Matter Description"
            btnSave.setOnClickListener(View.OnClickListener {
                startActivity(Intent(this, ConsultationNotesActivity::class.java))
            })
        }else{
            txtDisc.text = "Non-Retention Reason"
            btnSave.setOnClickListener(View.OnClickListener {
                startActivity(Intent(this, SendFreeAgreementActivity::class.java))
            }) }

        txtOne.setOnClickListener(View.OnClickListener {
            changeButton()
            txtOne.setBackgroundResource(R.drawable.blue_round_boarder);

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
        txtSix.setOnClickListener(View.OnClickListener {
            changeButton()
            txtSix.setBackgroundResource(R.drawable.blue_round_boarder);

        })
        txtSeven.setOnClickListener(View.OnClickListener {
            changeButton()
            txtSeven.setBackgroundResource(R.drawable.blue_round_boarder);

        })


    }
    fun changeButton(){
        txtOne.setBackgroundResource(R.drawable.cancel_options_boarder);
        txtTwo.setBackgroundResource(R.drawable.cancel_options_boarder);
        txtTree.setBackgroundResource(R.drawable.cancel_options_boarder);
        txtFour.setBackgroundResource(R.drawable.cancel_options_boarder);
        txtfive.setBackgroundResource(R.drawable.cancel_options_boarder);
        txtSix.setBackgroundResource(R.drawable.cancel_options_boarder);
        txtSeven.setBackgroundResource(R.drawable.cancel_options_boarder);

    }
}