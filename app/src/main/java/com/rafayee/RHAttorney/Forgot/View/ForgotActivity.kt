package com.rafayee.RH.Forgot.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.rafayee.RH.Forgot.Presenter.ForgotPresenter

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rafayee.RH.Utils.FocusChangeListener
import com.rafayee.RHAttorney.R


class ForgotActivity : AppCompatActivity() {
    lateinit var back:ImageView
    lateinit var send:ImageView
    lateinit var email:TextInputEditText
    lateinit var cardEmail: TextInputLayout
    lateinit var presenter: ForgotPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        supportActionBar?.hide()
        initVar()
        presenter= ForgotPresenter()
        presenter.forgotInstance(this,email)

        send.setOnClickListener {
            presenter.validations()
        }
        back.setOnClickListener { onBackPressed() }
        FocusChangeListener(this,cardEmail, email,30,30,65,0,30,30,65,0)
    }

    fun initVar(){
        back=findViewById(R.id.back)
        send=findViewById(R.id.send)
        email=findViewById(R.id.email)
        cardEmail = findViewById(R.id.cardEmail)
    }
}