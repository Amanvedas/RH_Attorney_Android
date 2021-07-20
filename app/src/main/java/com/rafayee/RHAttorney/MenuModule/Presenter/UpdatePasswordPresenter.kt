package com.rafayee.RHAttorney.MenuModule.Presenter

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.Login.Model.LoginResponseController
import com.rafayee.RHAttorney.MenuModule.View.IUpdate
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.vedas.apna.ServerConnections.AppStatus
import org.json.JSONException
import org.json.JSONObject
import java.util.regex.Pattern

class UpdatePasswordPresenter : RetrofitCallbacks.ServerResponseInterface {
    lateinit var context: Context
    lateinit var passwordOld: TextInputEditText
    lateinit var passwordNew: TextInputEditText
    lateinit var passwordConform: TextInputEditText
    lateinit var iUpdate: IUpdate

    fun UpdatePasswordPresenter(context : Context ,iUpdate: IUpdate,passwordOld: TextInputEditText,passwordNew: TextInputEditText
                                ,passwordConform: TextInputEditText){
        this.context=context
        this.passwordOld=passwordOld
        this.passwordNew=passwordNew
        this.passwordConform=passwordConform
        this.iUpdate = iUpdate
    }

    /*fun validations(){
        if(passwordOld.text?.trim()?.isNotEmpty()!!){
            if (passwordOld.text!!.length>=8 && passwordOld.text!!.length<=13){
                if (isValidPassword((passwordOld.text!!.toString()))){
                    if(passwordNew.text?.trim()?.isNotEmpty()!!){
                        if (passwordNew.text!!.length>=8 && passwordNew.text!!.length<=13){
                            if (isValidPassword((passwordNew.text!!.toString()))){
                                if (passwordConform.text?.trim()?.isNotEmpty()!!){
                                    if (passwordConform.text!!.length>=8 && passwordConform.text!!.length<=13){
                                        if (isValidPassword((passwordConform.text!!.toString()))){
                                            if (passwordNew.text.toString().equals(passwordConform.text.toString())){
                                                // iUpdate.finishView()
                                                if (progressDialog.checkNetwork(context)){
                                                    changePassword()
                                                }else{
                                                    progressDialog.hideProgress()
                                                    Toast.makeText(context,"Please check your network connection", Toast.LENGTH_SHORT).show()

                                                }
                                                //Toast.makeText(context,"Password updated successfully!!!", Toast.LENGTH_SHORT).show()
                                            }else{
                                                Toast.makeText(context,"Password mismatched", Toast.LENGTH_SHORT).show()
                                            }

                                        }else{
                                            Toast.makeText(context,"Password at least have 1 uppercase and lower case ,special character ",Toast.LENGTH_SHORT).show()
                                        }

                                    }else{
                                        Toast.makeText(context,"Password at least have 8 to 13 characters ", Toast.LENGTH_SHORT).show()
                                    }
                                }else{
                                    Toast.makeText(context,"Enter conform password", Toast.LENGTH_SHORT).show()
                                }

                            }
                            else{
                                Toast.makeText(context,"Password at least have 1 uppercase and lower case ,special character ",Toast.LENGTH_SHORT).show()
                            }

                        }else{
                            Toast.makeText(context,"Password at least have 8 to 13 characters ", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(context,"Enter new password", Toast.LENGTH_SHORT).show()
                    }

                }
                else{
                    Toast.makeText(context,"Password at least have 1 uppercase and lower case ,special character ",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context,"Password at least have 8 to 13 characters ", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(context,"Enter old password", Toast.LENGTH_SHORT).show()
        }
    }*/

    fun validations() {
        if (passwordOld.text?.trim()?.isNotEmpty()!!) {
            if (isValidPassword(passwordOld.text.toString())){
                if (passwordNew.text?.trim()?.isNotEmpty()!!) {
                    if (isValidPassword(passwordNew.text.toString())){
                        if (passwordConform.text?.trim()?.isNotEmpty()!!) {
                            if (isValidPassword(passwordConform.text.toString())){
                                if (!(passwordOld.text.toString()
                                        .equals(passwordNew.text.toString()))
                                ) {
                                    if (passwordNew.text.toString()
                                            .equals(passwordConform.text.toString())
                                    ) {
                                        if (AppStatus.getInstance(context).isConnected()) {
                                            ProgressDialog.getInstance().showProgress(context)
                                            changePassword()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "No Internet Connection!!!!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Password mismatched",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Cross check old password & new password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                passwordConform.requestFocus()
                                Toast.makeText(
                                    context,
                                    "Confirm Password length must be minimum 8 and maximum 13 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number, 1 Special Character and Spaces not allowed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            passwordConform.requestFocus()
                            Toast.makeText(context, "Enter conform password", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        passwordNew.requestFocus()
                        Toast.makeText(
                            context,
                            "New Password length must be minimum 8 and maximum 13 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number, 1 Special Character and Spaces not allowed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    passwordNew.requestFocus()
                    Toast.makeText(context, "Enter new password", Toast.LENGTH_SHORT).show()
                }
            } else {
                passwordOld.requestFocus()
                Toast.makeText(
                    context,
                    "Old Password length must be minimum 8 and maximum 13 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number, 1 Special Character and Spaces not allowed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            passwordOld.requestFocus()
            Toast.makeText(context, "Enter old password", Toast.LENGTH_SHORT).show()
        }
    }

    fun changePassword(){
        ProgressDialog.getInstance().showProgress(context)
        var passwordObject: JsonObject = JsonObject()
        val jsonObject = JSONObject()

        try {
            jsonObject.put("emailID", context.getSharedPreferences("LoginPref", 0).getString("emailID", "").toString() )
            jsonObject.put("oldPassword",passwordOld.text.toString())
            jsonObject.put("newPassword",passwordNew.text.toString())

            val jsonParser = JsonParser()
            passwordObject = jsonParser.parse(jsonObject.toString()) as JsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        //  RetrofitCallbacks.getInstace().forgotCallBack(context,forgotObject)

        /*val password : Retrofit = Retrofit.Builder().baseUrl(ServerApiCollection.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val passwordConnection =
            password.create(
                ServerApiCollection::class.java
            )

        val call = passwordConnection.updatePassword(passwordObject)
        RetrofitCallbacks.getInstace().apiCallBacks("UpdatePassword",call)*/
        RetrofitCallbacks.getInstace().apiCallBacks(context,"attorney/changepassword",passwordObject,"UpdatePassword")
    }

    fun isValidPassword(password: String?): Boolean {
        val expression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@!%*?&#])(?=\\S+$)[A-Za-z\\d$@!%*?&#]{8,13}"
        val pattern = Pattern.compile(expression)
        return !TextUtils.isEmpty(password) && pattern.matcher(password).matches()    }


    override fun failureCallBack(failureMsg: String?) {
        ProgressDialog.getInstance().hideProgress()
        Toast.makeText(context,failureMsg,Toast.LENGTH_SHORT).show()
    }

    override fun successCallBack(body: String?, from: String?) {
        ProgressDialog.getInstance().hideProgress()
        Log.e("lalald","ff:: "+body)
        Log.e("strignF",":: "+from)
        if (from.equals("UpdatePassword")){
            val loginObject : JSONObject = JSONObject(body)
            Log.e("lalald","ld:: "+loginObject)
            if(loginObject.get("response").equals(3)){
                iUpdate.finishView()
            }
        }
    }

}