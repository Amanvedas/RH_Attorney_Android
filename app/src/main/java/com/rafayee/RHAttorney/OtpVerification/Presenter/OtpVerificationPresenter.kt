package com.rafayee.RHAttorney.OtpVerification.Presenter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.NewPassword.View.NewPassword
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import org.json.JSONException
import org.json.JSONObject

class OtpVerificationPresenter: RetrofitCallbacks.ServerResponseInterface {
    lateinit var context: Context
    lateinit var strFrom : String
    lateinit var strEmail : String
    lateinit var ed1: EditText
    lateinit var ed2: EditText
    lateinit var ed3: EditText
    lateinit var ed4: EditText

    fun OtpVerificationInstance(context: Context,strEmail:String,ed1:EditText,ed2: EditText,ed3: EditText,ed4: EditText){
        this.context=context
        this.strEmail=strEmail
        this.ed1=ed1
        this.ed2=ed2
        this.ed3=ed3
        this.ed4=ed4
    }

    fun validations(str:String){
        strFrom = str
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
            val strOtp:String = ed1.text.toString()+ed2.text.toString()+ed3.text.toString()+ed4.text.toString()
           Log.e("otp","is:: "+strOtp+", "+strEmail)
            if (ProgressDialog.getInstance().checkNetwork(context)){
                otpVerifyApi(strEmail,strOtp)
            }else{
                ProgressDialog.getInstance().hideProgress()
                Toast.makeText(context,"Please check your internet connection ",Toast.LENGTH_SHORT).show()
            }

            // context.startActivity(Intent(context, NewPassword::class.java).putExtra("update",str))
        }
    }
    fun otpVerifyApi(email:String,otp:String){
        ProgressDialog.getInstance().showProgress(context)
        var otpObject: JsonObject = JsonObject()
        val jsonObject = JSONObject()

        try {
            jsonObject.put("emailID", email)
            jsonObject.put("otp",otp)
            val jsonParser = JsonParser()
            otpObject = jsonParser.parse(jsonObject.toString()) as JsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
        }
       /* val login : Retrofit = Retrofit.Builder().baseUrl(ServerApiCollection.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val loginConection =
            login.create(
                ServerApiCollection::class.java
            )

        val call = loginConection.verifyApi(otpObject)
        RetrofitCallbacks.getInstace().apiCallBacks("OTP",call)*/
        RetrofitCallbacks.getInstace().apiCallBacks(context,"attorney/verify",otpObject,"OTP")
    }

    fun forgotApi(context:Context,strF:String){
        strFrom=strF
        ProgressDialog.getInstance().showProgress(context)
        var forgotObject: JsonObject = JsonObject()
        val jsonObject = JSONObject()

        try {
            jsonObject.put("emailID", strEmail)
            val jsonParser = JsonParser()
            forgotObject = jsonParser.parse(jsonObject.toString()) as JsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        RetrofitCallbacks.getInstace().apiCallBacks(context,"attorney/forgotpassword",forgotObject,"Resend")
    }


    override fun failureCallBack(failureMsg: String?) {
        ProgressDialog.getInstance().hideProgress()
        Toast.makeText(context,failureMsg,Toast.LENGTH_SHORT).show()
    }

    override fun successCallBack(body: String?, from: String?) {
        ProgressDialog.getInstance().hideProgress()
        Log.e("from","is:: "+from)
        if (strFrom.equals("")){
            strFrom="normal"
        }
        if (from.equals("OTP")){
            val otpObject : JSONObject = JSONObject(body)
            Log.e("ressss","oo:: "+otpObject)
            if(otpObject.get("response").equals(3)){
              //  Toast.makeText(context, otpObject.get("message").toString(), Toast.LENGTH_LONG).show()
                context.startActivity(Intent(context, NewPassword::class.java)
                    .putExtra("strEmail",strEmail).putExtra("update",strFrom))
            }else if (otpObject.get("response").equals(0)){
                Toast.makeText(context, otpObject.get("message").toString(), Toast.LENGTH_LONG).show()
            }
            ProgressDialog.getInstance().hideProgress()
        }else if(from.equals("Resend")){
            val resendObject : JSONObject = JSONObject(body)
            if(resendObject.get("response").equals(3)){
                Toast.makeText(context, resendObject.get("message").toString(), Toast.LENGTH_LONG).show()
            }else if (resendObject.get("response").equals(0)){
                Toast.makeText(context, resendObject.get("message").toString(), Toast.LENGTH_LONG).show()
            }
            ProgressDialog.getInstance().hideProgress()
        }

    }
}