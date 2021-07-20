package com.rafayee.RHAttorney.NewPassword.Presenter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.MenuModule.PasswordUpdateSuccessfully
import com.rafayee.RHAttorney.NewPassword.View.PasswordResetSuccussfully
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.vedas.apna.ServerConnections.AppStatus
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.util.regex.Pattern

class NewPasswordPresenter : RetrofitCallbacks.ServerResponseInterface {
    lateinit var newPwd: TextInputEditText
    lateinit var cnfPwd: TextInputEditText
    lateinit var context: Context
    lateinit var emilID: String
    var strFrom: String = ""

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

    /*fun validations(str: String) {
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

    }*/

    fun validations(context: Context,str: String) {
        this.context = context
        Log.e("ododod", "lll " + str)
        this.strFrom = str
        Log.e("ododod", "ll " + strFrom)

        if(newPwd.text?.trim()?.isEmpty()!!){
            newPwd.requestFocus()
            Toast.makeText(context,"Enter new password", Toast.LENGTH_SHORT).show()
        }else if(cnfPwd.text?.trim()?.isEmpty()!!){
            cnfPwd.requestFocus()
            Toast.makeText(context,"Enter confirm password", Toast.LENGTH_SHORT).show()
        }else if (!isValidPassword(newPwd.text.toString())){
            newPwd.requestFocus()
            Toast.makeText(context,"New Password length must be minimum 8 and maximum 13 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number, 1 Special Character and Spaces not allowed.", Toast.LENGTH_SHORT).show()
        }else if (!isValidPassword(cnfPwd.text.toString())){
            cnfPwd.requestFocus()
            Toast.makeText(context,"Confirm Password length must be minimum 8 and maximum 13 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number, 1 Special Character and Spaces not allowed.", Toast.LENGTH_SHORT).show()
        } else if(newPwd.text.toString().equals(cnfPwd.text.toString())){
            if (AppStatus.getInstance(context).isConnected()) {
                ProgressDialog.getInstance().showProgress(context)
                resetPassword(cnfPwd.text.toString())
            } else {
                Toast.makeText(context, "No Internet Connection!!!!", Toast.LENGTH_SHORT).show()
            }
            //context.startActivity(Intent(context, PasswordResetSuccussfully::class.java))
        }else{
            cnfPwd.requestFocus()
            Toast.makeText(context,"Password not matched", Toast.LENGTH_SHORT).show()
        }
    }

    fun isValidPassword(password: String?): Boolean {
        val expression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@!%*?&#])(?=\\S+$)[A-Za-z\\d$@!%*?&#]{8,13}"
        val pattern = Pattern.compile(expression)
        return !TextUtils.isEmpty(password) && pattern.matcher(password).matches()
    }

    fun resetPassword(str: String) {
        var passwordObject = JsonObject()
        val jsonObject = JSONObject()

        try {
            jsonObject.put("emailID", emilID)
            jsonObject.put("password", str)
            val jsonParser = JsonParser()
            passwordObject = jsonParser.parse(jsonObject.toString()) as JsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.e("LOGINJSONss:", " $passwordObject")
        RetrofitCallbacks.getInstace().apiCallBacks(context,"attorney/reset_password",passwordObject,"ResetPassword")
    }

    override fun failureCallBack(failureMsg: String?) {
        Log.e("newpassword:", " $failureMsg")
        Toast.makeText(context,failureMsg,Toast.LENGTH_SHORT).show()
        ProgressDialog.getInstance().hideProgress()
    }
    override fun successCallBack(body: String?, from: String?) {
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(body)
            if (jsonObject.getString("response").equals("3")){
                if (from.equals("ResetPassword")) {
                    ProgressDialog.getInstance().hideProgress()
                    if (preferenceFileExist("LoginPref")) {
                        context.getSharedPreferences("LoginPref", 0).edit()
                            .putString("password", cnfPwd.text.toString()).apply()
                    }
                    if (strFrom.equals("")) {
                        strFrom = "normal"
                    }
                    if (strFrom.equals("update")) {
                        context.startActivity(
                            Intent(context, PasswordUpdateSuccessfully::class.java
                            )/*.putExtra("from",page)*/
                        )
                    } else {
                        context.startActivity(
                            Intent(
                                context,
                                PasswordResetSuccussfully::class.java
                            )
                        )
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    private fun preferenceFileExist(fileName: String): Boolean {
        val f = File("/data/data/" + context.getPackageName() + "/shared_prefs/"
                + fileName + ".xml");
        return f.exists()
    }
}