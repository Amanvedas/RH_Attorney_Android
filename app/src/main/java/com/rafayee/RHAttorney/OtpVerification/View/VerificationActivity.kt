package com.rafayee.RH.OtpVerification.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.rafayee.RH.OtpVerification.Presenter.OtpVerificationPresenter
import com.rafayee.RH.Utils.PinFieldFocusChangeListener
import com.rafayee.RH.Utils.PinInFiled
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks

class VerificationActivity : AppCompatActivity() {
    lateinit var verify: ImageView
    lateinit var back: ImageView
    lateinit var ed1: EditText
    lateinit var ed2: EditText
    lateinit var ed3: EditText
    lateinit var ed4: EditText
    lateinit var txtResend : TextView
    lateinit var presenter:OtpVerificationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        supportActionBar?.hide()
        initVar()
        PinInFiled(this,ed1, ed2, ed3, ed4)
        val bundle: Bundle? = intent.extras
        val strEmail : String? =  intent.getStringExtra("strEmail")
        val string: String? = intent.getStringExtra("isFrom")
        presenter= OtpVerificationPresenter()

        Log.e("email","is:: "+strEmail)

        if (strEmail != null) {
            presenter.OtpVerificationInstance(this,strEmail,ed1,ed2,ed3,ed4)
        }else{
            presenter.OtpVerificationInstance(this,"strEmail",ed1,ed2,ed3,ed4)

        }

        verify.setOnClickListener {
            Log.e("dadsf","fsd")
            if (string != null) {
                presenter.validations(string)
            }else{
                presenter.validations("")

            }
            string?.let { it1 -> presenter.validations(it1) }
        }
        txtResend.setOnClickListener(View.OnClickListener {
            string?.let { it1 -> presenter.forgotApi(this, it1) }
        })

        back.setOnClickListener {
            onBackPressed()
        }
        RetrofitCallbacks.getInstace().initializeServerInterface(presenter)

    }

    fun initVar(){
        verify = findViewById(R.id.verify)
        back = findViewById(R.id.back)
        ed1 = findViewById(R.id.ed1)
        ed2 = findViewById(R.id.ed2)
        ed3 = findViewById(R.id.ed3)
        ed4 = findViewById(R.id.ed4)
        txtResend = findViewById(R.id.txt_resend)


        PinFieldFocusChangeListener(this,ed1,5,5,5,5,5,5,5,5)
        PinFieldFocusChangeListener(this,ed2,5,5,5,5,5,5,5,5)
        PinFieldFocusChangeListener(this,ed3,5,5,5,5,5,5,5,5)
        PinFieldFocusChangeListener(this,ed4,5,5,5,5,5,5,5,5)
    }
}