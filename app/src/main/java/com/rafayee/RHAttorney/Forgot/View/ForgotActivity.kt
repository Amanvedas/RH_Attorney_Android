package com.rafayee.RHAttorney.Forgot.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rafayee.RHAttorney.Forgot.Presenter.ForgotPresenter
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.rafayee.RHAttorney.Utils.FocusChangeListener


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
        val bundle: Bundle? = intent.extras
        var  string: String? = intent.getStringExtra("update")
        presenter= ForgotPresenter()
        presenter.forgotInstance(this,email)

        send.setOnClickListener {
            Log.e("afdaf","fff "+string)
            RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
            string?.let { it1 -> presenter.validations(it1) }
        }
        back.setOnClickListener { onBackPressed() }
        FocusChangeListener(this,cardEmail, email,30,30,65,0,30,30,65,0)
        RetrofitCallbacks.getInstace().initializeServerInterface(presenter)

    }

    fun initVar(){
        back=findViewById(R.id.back)
        send=findViewById(R.id.send)
        email=findViewById(R.id.email)
        cardEmail = findViewById(R.id.cardEmail)

    }
}