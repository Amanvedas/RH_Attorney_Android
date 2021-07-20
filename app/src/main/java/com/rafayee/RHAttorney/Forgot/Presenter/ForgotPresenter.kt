package com.rafayee.RHAttorney.Forgot.Presenter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.OtpVerification.View.VerificationActivity
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern

class ForgotPresenter : RetrofitCallbacks.ServerResponseInterface {
    lateinit var email: TextInputEditText
    lateinit var context: Context
    lateinit var strFrom :String
    fun forgotInstance(context: Context, email: TextInputEditText) {
        this.email = email
        this.context = context
    }

    fun validations(str:String) {
        if (email.text?.trim()?.isNotEmpty()!!) {
            if (validEmail(email.text.toString())) {
                Log.e("afdaf","ddd "+str)
                if (ProgressDialog.getInstance().checkNetwork(context)){
                    strFrom = str
                    forgotApi(context,email.text.toString())
                }else{
                    ProgressDialog.getInstance().hideProgress()
                    Toast.makeText(context,"Please check your internet connection ",Toast.LENGTH_SHORT).show()
                }
            } else {
                email.requestFocus()
                Toast.makeText(context, "Enter valid email", Toast.LENGTH_SHORT).show()
            }
        } else {
            email.requestFocus()
            Toast.makeText(context, "Enter email", Toast.LENGTH_SHORT).show()
        }
    }
    fun forgotApi(context:Context,email:String){
        ProgressDialog.getInstance().showProgress(context)
        var forgotObject: JsonObject = JsonObject()
        val jsonObject = JSONObject()

        try {
            jsonObject.put("emailID", email)
            val jsonParser = JsonParser()
            forgotObject = jsonParser.parse(jsonObject.toString()) as JsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
        }
      //  RetrofitCallbacks.getInstace().forgotCallBack(context,forgotObject)
        RetrofitCallbacks.getInstace().apiCallBacks(context,"attorney/forgotpassword",forgotObject,"Forgot")
    }


    private fun validEmail(target: String?): Boolean {
        //val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val emailPattern = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}"
        val pattern = Pattern.compile(emailPattern)
        return !TextUtils.isEmpty(target) && pattern.matcher(target).matches()
    }

    override fun failureCallBack(failureMsg: String?) {
        ProgressDialog.getInstance().hideProgress()
        Log.e("Working","no:: ")
        Toast.makeText(context, failureMsg, Toast.LENGTH_SHORT).show()
    }

    override fun successCallBack(body: String?, from: String?) {
        ProgressDialog.getInstance().hideProgress()
        Log.e("lalald","ff:: "+body)
        Log.e("strignF",":: "+strFrom)
        if (strFrom.equals("")){
            strFrom="normal"
        }
        Log.e("strEmail","Is:: "+email.text.toString())
        if (from.equals("Forgot")){
            val loginObject : JSONObject = JSONObject(body)
            Log.e("lalald","ld:: "+loginObject)
            if(loginObject.get("response").equals(3)){
                context.startActivity(Intent(context, VerificationActivity::class.java)
                    .putExtra("strEmail",email.text.toString())
                    .putExtra("isFrom",strFrom))

            }else{
                Toast.makeText(context,loginObject.get("message").toString(),Toast.LENGTH_SHORT).show()
            }
        }

    }


}