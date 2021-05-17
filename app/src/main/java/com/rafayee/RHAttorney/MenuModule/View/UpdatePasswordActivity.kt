package com.rafayee.RH.MenuModule.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rafayee.RH.Forgot.View.ForgotActivity
import com.rafayee.RH.MenuModule.PasswordUpdateSuccessfully
import com.rafayee.RH.MenuModule.Presenter.UpdatePasswordPresenter
import com.rafayee.RH.Utils.FocusChangeListener
import com.rafayee.RHAttorney.R

class UpdatePasswordActivity : AppCompatActivity(),IUpdate {
    lateinit var presenter : UpdatePasswordPresenter
    lateinit var cardOldPwd : TextInputLayout
    lateinit var cardNewPwd : TextInputLayout
    lateinit var cardPwd : TextInputLayout
    lateinit var pwdOld: TextInputEditText
    lateinit var pwdNew: TextInputEditText
    lateinit var pwdConform: TextInputEditText
    lateinit var imgDone : ImageView
    lateinit var back : ImageView
    lateinit var txtForgot : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)
        supportActionBar?.hide()
        intialization()
        clickEvents()
    }

    fun clickEvents(){
        txtForgot.setOnClickListener {
            startActivity(Intent(this, ForgotActivity::class.java))
        }
        imgDone.setOnClickListener {
            presenter.validations()
        }
        back.setOnClickListener { onBackPressed() }
    }

    fun intialization(){
        pwdConform = findViewById(R.id.txt_conform_password)
        pwdNew = findViewById(R.id.pwd)
        back = findViewById(R.id.back)
        pwdOld = findViewById(R.id.txt_old_password)
        txtForgot = findViewById(R.id.txt_forgot)
        imgDone = findViewById(R.id.img_done)
        cardOldPwd = findViewById(R.id.cardOldPwd)
        cardNewPwd = findViewById(R.id.cardNewPwd)
        cardPwd = findViewById(R.id.cardPwd)
        presenter = UpdatePasswordPresenter()
        presenter.UpdatePasswordPresenter(this,this,pwdOld,pwdNew,pwdConform)

        FocusChangeListener(this,cardOldPwd, pwdOld,0,0,10,5,2,2,10,5)
        FocusChangeListener(this,cardNewPwd, pwdNew,0,0,10,5,2,2,10,5)
        FocusChangeListener(this,cardPwd, pwdConform,0,0,10,5,2,2,10,5)
    }

    override fun finishView() {
        finish()
        startActivity(Intent(this@UpdatePasswordActivity, PasswordUpdateSuccessfully::class.java))
    }
}
