package com.rafayee.RH.NewPassword.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.rafayee.RH.NewPassword.Presenter.NewPasswordPresenter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rafayee.RH.Utils.FocusChangeListener
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks

class NewPassword : AppCompatActivity() {
    lateinit var back:ImageView
    lateinit var done:ImageView
    lateinit var newPwd:TextInputEditText
    lateinit var cnfPwd: TextInputEditText
    lateinit var cardNewPwd: TextInputLayout
    lateinit var cardPwd: TextInputLayout
    lateinit var presenter: NewPasswordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)
        supportActionBar?.hide()
        initVar()
        val bundle: Bundle? = intent.extras
        val string: String? = intent.getStringExtra("strEmail")
        var stringFrom : String?=intent.getStringExtra("update")
        presenter= NewPasswordPresenter()
        if (string != null) {
            presenter.NewPasswordInstance(this,string,newPwd, cnfPwd)
        }
        RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
        done.setOnClickListener{
            if (stringFrom!=null){
                stringFrom?.let { it1 -> presenter.validations(it1) }
            }else{
                stringFrom=""
                stringFrom?.let { it1 -> presenter.validations(it1) }

            }

/*
            if (stringFrom != null) {
                presenter.validations(stringFrom)
            }
*/

        }
        back.setOnClickListener { onBackPressed() }

        FocusChangeListener(this,cardNewPwd, newPwd,0,0,10,5,2,2,10,5)
        FocusChangeListener(this,cardPwd, cnfPwd,0,0,10,5,2,2,10,5)
    }

    fun initVar(){
        back=findViewById(R.id.back)
        done=findViewById(R.id.done)
        newPwd=findViewById(R.id.newPwd)
        cnfPwd= findViewById(R.id.cnfPwd)
        cardNewPwd = findViewById(R.id.cardNewPwd)
        cardPwd = findViewById(R.id.cardPwd)
    }
}