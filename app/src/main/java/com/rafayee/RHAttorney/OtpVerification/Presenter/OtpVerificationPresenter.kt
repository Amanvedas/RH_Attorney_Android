package com.rafayee.RH.OtpVerification.Presenter

import android.content.Context
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import com.rafayee.RH.NewPassword.View.NewPassword
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks

class OtpVerificationPresenter: RetrofitCallbacks.ServerResponseInterface {
    lateinit var context: Context
    lateinit var ed1: EditText
    lateinit var ed2: EditText
    lateinit var ed3: EditText
    lateinit var ed4: EditText

    fun OtpVerificationInstance(context: Context,ed1:EditText,ed2: EditText,ed3: EditText,ed4: EditText){
        this.context=context
        this.ed1=ed1
        this.ed2=ed2
        this.ed3=ed3
        this.ed4=ed4
    }

    fun validations(){
        if (ed1.text?.trim()?.isEmpty()!!) {
            ed1.requestFocus()
            Toast.makeText(context, "Enter valid pin", Toast.LENGTH_SHORT).show()
        } else if (ed2.text?.trim()?.isEmpty()!!) {
            ed2.requestFocus()
            Toast.makeText(context, "Enter valid pin", Toast.LENGTH_SHORT).show()
        } else if (ed3.text?.trim()?.isEmpty()!!) {
            ed3.requestFocus()
            Toast.makeText(context, "Enter valid pin", Toast.LENGTH_SHORT).show()
        } else if (ed4.text?.trim()?.isEmpty()!!) {
            ed4.requestFocus()
            Toast.makeText(context, "Enter valid pin", Toast.LENGTH_SHORT).show()
        } else {
            context.startActivity(Intent(context, NewPassword::class.java))
        }
    }

    override fun failureCallBack(failureMsg: String?) {
        //TODO("Not yet implemented")
    }

    override fun successCallBack(body: String?, from: String?) {
       // TODO("Not yet implemented")
    }
}