package com.rafayee.RH.NewPassword.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.rafayee.RH.NewPassword.Presenter.NewPasswordPresenter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rafayee.RH.Utils.FocusChangeListener
import com.rafayee.RHAttorney.R

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
        presenter= NewPasswordPresenter()
        presenter.NewPasswordInstance(this,newPwd, cnfPwd)
        done.setOnClickListener{
            presenter.validations()
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