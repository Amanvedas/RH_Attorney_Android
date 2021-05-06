package com.rafayee.RH.OtpVerification.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import com.rafayee.RH.OtpVerification.Presenter.OtpVerificationPresenter
import com.rafayee.RH.Utils.PinFieldFocusChangeListener
import com.rafayee.RH.Utils.PinInFiled
import com.rafayee.RHAttorney.R

class VerificationActivity : AppCompatActivity() {
    lateinit var verify: ImageView
    lateinit var back: ImageView
    lateinit var ed1: EditText
    lateinit var ed2: EditText
    lateinit var ed3: EditText
    lateinit var ed4: EditText
    lateinit var presenter:OtpVerificationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        supportActionBar?.hide()
        initVar()
        PinInFiled(this,ed1, ed2, ed3, ed4)

        presenter= OtpVerificationPresenter()
        presenter.OtpVerificationInstance(this,ed1,ed2,ed3,ed4)

        verify.setOnClickListener {
            presenter.validations()
        }

        back.setOnClickListener {
            onBackPressed()
        }
    }

    fun initVar(){
        verify = findViewById(R.id.verify)
        back = findViewById(R.id.back)
        ed1 = findViewById(R.id.ed1)
        ed2 = findViewById(R.id.ed2)
        ed3 = findViewById(R.id.ed3)
        ed4 = findViewById(R.id.ed4)

        PinFieldFocusChangeListener(this,ed1,5,5,5,5,5,5,5,5)
        PinFieldFocusChangeListener(this,ed2,5,5,5,5,5,5,5,5)
        PinFieldFocusChangeListener(this,ed3,5,5,5,5,5,5,5,5)
        PinFieldFocusChangeListener(this,ed4,5,5,5,5,5,5,5,5)
    }
}