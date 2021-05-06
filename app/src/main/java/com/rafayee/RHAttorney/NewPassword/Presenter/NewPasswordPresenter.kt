package com.rafayee.RH.NewPassword.Presenter

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.rafayee.RH.NewPassword.View.PasswordResetSuccussfully
import com.google.android.material.textfield.TextInputEditText
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks

class NewPasswordPresenter: RetrofitCallbacks.ServerResponseInterface {
    lateinit var newPwd: TextInputEditText
    lateinit var cnfPwd: TextInputEditText
    lateinit var context: Context

    fun NewPasswordInstance(context: Context,newPwd:TextInputEditText,cnfPwd:TextInputEditText){
        this.context=context
        this.newPwd=newPwd
        this.cnfPwd=cnfPwd
    }

    fun validations() {
        if(newPwd.text?.trim()?.isEmpty()!!){
            newPwd.requestFocus()
            Toast.makeText(context,"Enter new password", Toast.LENGTH_SHORT).show()
        }else if(cnfPwd.text?.trim()?.isEmpty()!!){
            cnfPwd.requestFocus()
            Toast.makeText(context,"Enter confirm password", Toast.LENGTH_SHORT).show()
        }else if(newPwd.text.toString().equals(cnfPwd.text.toString())){
            context.startActivity(Intent(context, PasswordResetSuccussfully::class.java))
        }else{
            cnfPwd.requestFocus()
            Toast.makeText(context,"Password not matched", Toast.LENGTH_SHORT).show()
        }
    }

    override fun failureCallBack(failureMsg: String?) {
      //  TODO("Not yet implemented")
    }

    override fun successCallBack(body: String?, from: String?) {
        //TODO("Not yet implemented")
    }
}