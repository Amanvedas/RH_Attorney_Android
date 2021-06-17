package com.rafayee.RH.NewPassword.Presenter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.rafayee.RH.NewPassword.View.PasswordResetSuccussfully
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rafayee.RH.MenuModule.PasswordUpdateSuccessfully
import com.rafayee.RH.NewPassword.View.NewPassword
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern

class NewPasswordPresenter : RetrofitCallbacks.ServerResponseInterface {
    lateinit var newPwd: TextInputEditText
    lateinit var cnfPwd: TextInputEditText
    lateinit var context: Context
    lateinit var emilID: String
    lateinit var strFrom: String
    var progressDialog: ProgressDialog = ProgressDialog()

    fun NewPasswordInstance(
        context: Context,
        strEmailID: String,
        newPwd: TextInputEditText,
        cnfPwd: TextInputEditText
    ) {
        this.context = context
        this.emilID = strEmailID
        this.newPwd = newPwd
        this.cnfPwd = cnfPwd
    }

    fun validations(str: String) {
        Log.e("ododod", "lll " + str)
        strFrom = str
        Log.e("ododod", "ll " + strFrom)

        if (newPwd.text?.trim()?.isEmpty()!!) {
            newPwd.requestFocus()
            Toast.makeText(context, "Enter new password", Toast.LENGTH_SHORT).show()
        } else if (newPwd.text!!.length >= 8 && newPwd.text!!.length <= 13) {
            if (isValidPassword((newPwd.text!!.toString()))) {
                if (cnfPwd.text?.trim()?.isEmpty()!!) {
                    cnfPwd.requestFocus()
                    Toast.makeText(context, "Enter confirm password", Toast.LENGTH_SHORT).show()
                } else if (cnfPwd.text!!.length >= 8 && cnfPwd.text!!.length <= 13) {
                    if (isValidPassword((newPwd.text!!.toString()))) {
                        if (newPwd.text.toString().equals(cnfPwd.text.toString())) {
                            if (progressDialog.checkNetwork(context)) {
                                resetPassword(cnfPwd.text.toString())
                            } else {
                                progressDialog.hideProgress()
                                Toast.makeText(
                                    context,
                                    "Please check your internet connection ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else {
                            cnfPwd.requestFocus()
                            Toast.makeText(context, "Password not matched", Toast.LENGTH_SHORT)
                                .show()
                        }

                    } else {
                        Toast.makeText(
                            context,
                            "Password at least have 1 uppercase and lower case ,special character ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Password at least have 8 to 13 characters ",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(
                    context,
                    "Password at least have 1 uppercase and lower case ,special character ",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } else {
            Toast.makeText(
                context,
                "Password at least have 8 to 13 characters ",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun isValidPassword(password: String?): Boolean {
        /* val pattern: Pattern
         val matcher: Matcher
         val PASSWORD_PATTERN =
             "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
         pattern = Pattern.compile(PASSWORD_PATTERN)
         matcher = pattern.matcher(password)
         return matcher.matches()*/
        val expression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@!%*?&#])(?=\\S+$)[A-Za-z\\d$@!%*?&#]{8,13}"
        val pattern = Pattern.compile(expression)
        return !TextUtils.isEmpty(password) && pattern.matcher(password).matches()
    }

    fun resetPassword(str: String) {
        progressDialog.showProgress(context)
        var passwordObject: JsonObject = JsonObject()
        val jsonObject = JSONObject()

        try {
            jsonObject.put("emailID", emilID)
            jsonObject.put("password", str)
            val jsonParser = JsonParser()
            passwordObject = jsonParser.parse(jsonObject.toString()) as JsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        //  RetrofitCallbacks.getInstace().forgotCallBack(context,forgotObject)

        val password: Retrofit = Retrofit.Builder().baseUrl(ServerApiCollection.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val passwordConnection =
            password.create(
                ServerApiCollection::class.java
            )

        val call = passwordConnection.resetPassword(passwordObject)
        RetrofitCallbacks.getInstace().apiCallBacks("ResetPassword", call)

    }

    override fun failureCallBack(failureMsg: String?) {
        progressDialog.hideProgress()
        //  TODO("Not yet implemented")
    }

    override fun successCallBack(body: String?, from: String?) {
        progressDialog.hideProgress()

        if (from.equals("ResetPassword")) {
            Log.e("strFrom", "from:: " + strFrom)
            if (strFrom.equals("")) {
                strFrom = "normal"
            }
            val otpObject: JSONObject = JSONObject(body)
            Log.e("ressss", "oo:: " + otpObject)
            if (otpObject.get("response").equals(3)) {
               /* Toast.makeText(context, otpObject.get("message").toString(), Toast.LENGTH_LONG)
                    .show()*/
                if (strFrom.equals("update")) {
                    context.startActivity(Intent(context, PasswordUpdateSuccessfully::class.java))

                } else {
                    context.startActivity(Intent(context, PasswordResetSuccussfully::class.java))

                }
            } else if (otpObject.get("response").equals(0)) {
                Toast.makeText(context, otpObject.get("message").toString(), Toast.LENGTH_LONG)
                    .show()

            }
        }

    }
}