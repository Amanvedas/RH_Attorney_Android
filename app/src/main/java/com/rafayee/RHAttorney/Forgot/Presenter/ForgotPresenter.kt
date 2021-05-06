package com.rafayee.RH.Forgot.Presenter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.rafayee.RH.OtpVerification.View.VerificationActivity
import com.google.android.material.textfield.TextInputEditText
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import java.util.regex.Pattern

class ForgotPresenter : RetrofitCallbacks.ServerResponseInterface {
    lateinit var email: TextInputEditText
    lateinit var context: Context

    fun forgotInstance(context: Context, email: TextInputEditText) {
        this.email = email
        this.context = context
    }

    fun validations() {
        if (email.text?.trim()?.isNotEmpty()!!) {
            if (validEmail(email.text.toString())) {
                context.startActivity(Intent(context, VerificationActivity::class.java))
            } else {
                email.requestFocus()
                Toast.makeText(context, "Enter valid email", Toast.LENGTH_SHORT).show()
            }
        } else {
            email.requestFocus()
            Toast.makeText(context, "Enter email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validEmail(target: String?): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val pattern = Pattern.compile(emailPattern)
        return !TextUtils.isEmpty(target) && pattern.matcher(target).matches()
    }

    override fun failureCallBack(failureMsg: String?) {
        // TODO("Not yet implemented")
    }

    override fun successCallBack(body: String?, from: String?) {
        //TODO("Not yet implemented")
    }
}