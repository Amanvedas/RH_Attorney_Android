package com.rafayee.RH.Login.Presenter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rafayee.RH.Forgot.View.ForgotActivity
import com.rafayee.RH.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RH.Login.View.LoginView
import com.rafayee.RH.Utils.FocusChangeListener
import com.rafayee.RH.Utils.PinFieldFocusChangeListener
import com.rafayee.RH.Utils.PinInFiled
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.Login.LoginResponseController
import com.rafayee.RHAttorney.Login.LoginResponseModel
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection.BASE_URL
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import androidx.core.content.ContextCompat.getSystemService as getSystemService1


class LoginPresenter: RetrofitCallbacks.ServerResponseInterface {
    lateinit var context:Context
    lateinit var rememberSwitch : SwitchCompat
    var progressDialog: ProgressDialog = ProgressDialog()
    lateinit var username: TextInputEditText
    lateinit var password: TextInputEditText
    private var loginView: LoginView? = null
    lateinit var deviceId : String
    lateinit var deviceToken : String
    var SP: SharedPreferences? = null
    var filename = "Valustoringfile"
    var SPToken: SharedPreferences? = null
    var preferID = "TokenID"
    lateinit var editToken: SharedPreferences.Editor
    lateinit var editit: SharedPreferences.Editor
    fun LoginInstance(
        context: Context,
        loginView: LoginView,
        username: TextInputEditText,
        password: TextInputEditText,
        deviceId : String,
        deviceToken : String,
        rememberSwitch : SwitchCompat
    ){
        this.context=context
        this.username=username
        this.password=password
        this.loginView = loginView
        this.deviceId = deviceId
        this.deviceToken = deviceToken
        this.rememberSwitch = rememberSwitch
    }

    fun validations(){
        if(username.text?.trim()?.isNotEmpty()!!){
            if (username.text.toString().contains("@")){
                if (validEmail(username.text.toString())){
                    if(password.text?.trim()?.isNotEmpty()!!){
                        if (password.text!!.length>=8 && password.text!!.length<=13){

                            if (isValidPassword(password.text?.trim().toString())){
                                Log.e("isNetwork","Connected:: "+progressDialog.checkNetwork(context))
                                if (progressDialog.checkNetwork(context)){
                                    LoginApi(context,username.text.toString(),password.text.toString(),deviceToken)
                                }else{
                                    progressDialog.hideProgress()
                                    Toast.makeText(context,"Please check your internet connection ",Toast.LENGTH_SHORT).show()

                                }
                                //LoginApi(context,username.text.toString(),password.text.toString(),"d2","dt5")
                            }else{
                                Toast.makeText(context,"Password at least have 1 uppercase and lower case ,special character ",Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(context,"Password at least have 8 to 13 characters ",Toast.LENGTH_SHORT).show()

                        }
                        Log.e("length","is:: "+ password.text!!.length)
                        // showCustomDialogSuccess()
                    }else{
                        loginView?.notifyUser("Enter password")
                    }
                }else{
                    loginView?.notifyUser("Enter valid email id")
                }

            }
            else{
                if (username.text.toString().length==10){
                    if (isValidPhoneallzeros(username.text.toString())){
                        if(password.text?.trim()?.isNotEmpty()!!){
                            if (password.text!!.length>=8 && password.text!!.length<=13){

                                if (isValidPassword(password.text?.trim().toString())){
                                    Log.e("isNetwork","Connected:: "+progressDialog.checkNetwork(context))
                                    if (progressDialog.checkNetwork(context)){
                                        LoginApi(context,username.text.toString(),password.text.toString(),deviceToken)
                                    }else{
                                        progressDialog.hideProgress()
                                        Toast.makeText(context,"Please check your internet connection ",Toast.LENGTH_SHORT).show()

                                    }
                                    //LoginApi(context,username.text.toString(),password.text.toString(),"d2","dt5")
                                }else{
                                    Toast.makeText(context,"Password at least have 1 uppercase and lower case ,special character ",Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                Toast.makeText(context,"Password at least have 8 to 13 characters ",Toast.LENGTH_SHORT).show()

                            }
                            Log.e("length","is:: "+ password.text!!.length)
                            // showCustomDialogSuccess()
                        }else{
                            loginView?.notifyUser("Enter password")
                        }

                    }else{
                        Toast.makeText(context,"Phone number must have 10 digits(all zero's doesn't allow)",Toast.LENGTH_SHORT).show()

                    }

                }else{
                    Toast.makeText(context,"Enter valid phone number",Toast.LENGTH_SHORT).show()

                }
            }

        }else{
            loginView?.notifyUser("Enter email/phone number")
        }
    }


    fun otpAPI(context:Context,email:String,otp:String){
        var otpObject:JsonObject = JsonObject()
        val jsonObject = JSONObject()

        try {
            jsonObject.put("emailID", email)
            jsonObject.put("otp",otp)

            val jsonParser = JsonParser()
            otpObject = jsonParser.parse(jsonObject.toString()) as JsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        RetrofitCallbacks.getInstace().OTPApiCall(context,otpObject)
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

    fun LoginApi(context:Context,email:String,password:String,deviceToken:String){
        progressDialog.showProgress(context)
        var loginObject:JsonObject = JsonObject()
        val jsonObject = JSONObject()

        try {
            jsonObject.put("emailID", email)
            jsonObject.put("password",password)
            jsonObject.put("deviceID",deviceId)
            jsonObject.put("deviceToken",deviceToken)
            jsonObject.put("deviceType","mobile")
            val jsonParser = JsonParser()
            loginObject = jsonParser.parse(jsonObject.toString()) as JsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
        }
      //  RetrofitCallbacks.getInstace().loginCallBacks(context,loginObject)
        val login : Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val loginConection =
            login.create(
                ServerApiCollection::class.java
            )

        val call = loginConection.LoginApi(loginObject)
        RetrofitCallbacks.getInstace().apiCallBacks("Login",call)

    }

    private fun showCustomDialogSuccess() {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.verification)
        dialog.setTitle("Title...")

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        var code:String
        val ed1: EditText = dialog.findViewById(R.id.ed1)
        val ed2: EditText = dialog.findViewById(R.id.ed2)
        val ed3: EditText = dialog.findViewById(R.id.ed3)
        val ed4: EditText = dialog.findViewById(R.id.ed4)
        val verify: ImageView = dialog.findViewById(R.id.verify)

        PinInFiled(context,ed1,ed2,ed3,ed4)

        PinFieldFocusChangeListener(context,ed1,3,3,0,0,5,5,5,5)
        PinFieldFocusChangeListener(context,ed2,3,3,0,0,5,5,5,5)
        PinFieldFocusChangeListener(context,ed3,3,3,0,0,5,5,5,5)
        PinFieldFocusChangeListener(context,ed4,3,3,0,0,5,5,5,5)

        verify.setOnClickListener {
            if(ed1.text?.trim()?.isEmpty()!!) {
                ed1.requestFocus()
                Toast.makeText(context,"Enter valid pin",Toast.LENGTH_SHORT).show()
            }else if(ed2.text?.trim()?.isEmpty()!!){
                ed2.requestFocus()
                Toast.makeText(context,"Enter valid pin",Toast.LENGTH_SHORT).show()
            } else if(ed3.text?.trim()?.isEmpty()!!){
                ed3.requestFocus()
                Toast.makeText(context,"Enter valid pin",Toast.LENGTH_SHORT).show()
            }else if(ed4.text?.trim()?.isEmpty()!!){
                ed4.requestFocus()
                Toast.makeText(context,"Enter valid pin",Toast.LENGTH_SHORT).show()
            }else{
                dialog.dismiss()
                otpAPI(context,"bharath.civil123@gmail.com","1234")
                context.startActivity(Intent(context, HomeWithBottomTabsActivity::class.java))

            }
        }
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    fun bottomSheet(context: Context, finger: Boolean) {
        val cameraBottomSheetDialog = BottomSheetDialog(Objects.requireNonNull(context), R.style.BottomSheetDialogTheme)
        val dialogView: View = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null)
        cameraBottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        val hintTextSensor: TextView = dialogView.findViewById(R.id.hintTextSensor)
        val idName: TextView = dialogView.findViewById(R.id.idName)
        val bottomImage: ImageView = dialogView.findViewById(R.id.bottomImage)
        val back: ImageView = dialogView.findViewById(R.id.back)
        val login: ImageView = dialogView.findViewById(R.id.login)
        val editTextLay: LinearLayout = dialogView.findViewById(R.id.editTextLay)
        val fingerPrintLay: LinearLayout = dialogView.findViewById(R.id.fingerPrintLay)
        val textSentence: TextView = dialogView.findViewById(R.id.textSentence)
        val forgot: TextView = dialogView.findViewById(R.id.forgot)
        val topImage: ImageView = dialogView.findViewById(R.id.topImage)
        val cardEmail: TextInputLayout = dialogView.findViewById(R.id.cardEmail)
        val cardPwd: TextInputLayout = dialogView.findViewById(R.id.cardPwd)
        val email: TextInputEditText = dialogView.findViewById(R.id.email)
        val pwd: TextInputEditText = dialogView.findViewById(R.id.pwd)

        FocusChangeListener(context,cardEmail, email,0,0,10,5,5,5,10,5)
        FocusChangeListener(context,cardPwd, pwd,0,0,10,5,5,5,10,5)

        if (finger) {
            idName.text = "Touch ID"
            hintTextSensor.text = "Touch the fingerprint sensor"
            textSentence.text = "To enable Touch ID please first login to R&H"
            topImage.setImageDrawable(context.resources.getDrawable(R.drawable.fingerprint_2))
            bottomImage.setImageDrawable(context.resources.getDrawable(R.drawable.fingerprint_3))
        } else {
            idName.text = "Face ID"
            hintTextSensor.text = "Look directly at your front camera"
            textSentence.text = "To enable Face ID please first login to R&H"
            topImage.setImageDrawable(context.resources.getDrawable(R.drawable.face_id_2))
            bottomImage.setImageDrawable(context.resources.getDrawable(R.drawable.face_id_3))
        }
        back.setOnClickListener { cameraBottomSheetDialog.dismiss() }
        forgot.setOnClickListener {
            cameraBottomSheetDialog.dismiss()
            context.startActivity(Intent(context, ForgotActivity::class.java))
        }
        login.setOnClickListener {
            if (email.text?.trim()?.isNotEmpty()!!) {
                if (validEmail(email.text.toString())) {
                    if (pwd.text?.trim()?.isNotEmpty()!!) {
                        if (finger) {
                            editTextLay.visibility = View.GONE
                            fingerPrintLay.visibility = View.VISIBLE
                            textSentence.text = "Login to R&H"
                            idName.text = "Touch ID"
                            hintTextSensor.text = "Touch the fingerprint sensor"
                            topImage.setImageDrawable(context.resources.getDrawable(R.drawable.fingerprint_2))
                            bottomImage.setImageDrawable(context.resources.getDrawable(R.drawable.fingerprint_3))
                            loginView?.finger(context)

                        } else {
                            editTextLay.visibility = View.GONE
                            fingerPrintLay.visibility = View.VISIBLE
                            textSentence.text = "Login to R&H"
                            idName.text = "Face ID"
                            hintTextSensor.text = "Look directly at your front camera"
                            topImage.setImageDrawable(context.resources.getDrawable(R.drawable.face_id_2))
                            bottomImage.setImageDrawable(context.resources.getDrawable(R.drawable.face_id_3))
                            loginView?.face(context)
                        }
                    } else {
                        Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Enter valid email", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Enter email", Toast.LENGTH_SHORT).show()
            }
        }
        cameraBottomSheetDialog.setContentView(dialogView)
        cameraBottomSheetDialog.show()
    }

    private fun validEmail(target: String?): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val pattern = Pattern.compile(emailPattern)
        return !TextUtils.isEmpty(target) && pattern.matcher(target).matches()
    }

    override fun failureCallBack(failureMsg: String?) {
        progressDialog.hideProgress()
        //("Not yet implemented")
    }
    fun isValidPhoneallzeros(phone: String?): Boolean {
        val expression = "^(?!0+$)\\d*$"  /*"^(?!0+$)\\d{10,}$"*/
        val pattern = Pattern.compile(expression)
        return !TextUtils.isEmpty(phone)
                && pattern.matcher(phone).matches()
    }
    override fun successCallBack(body: String?, from: String?) {
        progressDialog.hideProgress()
        if (from.equals("Login")){
            Log.e("ressss","ff:: "+body)
            val loginObject : JSONObject = JSONObject(body)
            Log.e("ressss","oo:: "+loginObject)
            if (loginObject.get("response").equals(3)){
                SP = context.getSharedPreferences(filename, 0);
                SPToken = context.getSharedPreferences(preferID, 0);
                editit = SP!!.edit()
                editToken =SPToken!!.edit()
                if (rememberSwitch.isChecked){
                    editToken.putString("key1", username.text.toString())
                    editToken.putString("key2", password.text.toString())
                   /* editit.putString("key1", username.text.toString())
                    editit.putString("key2", password.text.toString())

                    editit.apply()*/
                    editToken.apply()

                }
                editit.putString("data", body)

                editToken.putString("token", deviceToken)
                editToken.putString("id", deviceId)
                editToken.apply()
                editit.putString("key3","isLogin")
                editit.apply()

               // Toast.makeText(context, loginObject.get("message").toString(), Toast.LENGTH_LONG).show()
                val gson = Gson()
                val loginResponseModel: LoginResponseModel  = gson.fromJson(body, LoginResponseModel::class.java)
                LoginResponseController.instance!!.loginResponseModel = loginResponseModel
                Log.e("data","res:: "+gson.toJson(LoginResponseController.myObj?.loginResponseModel))
                context.startActivity(Intent(context, HomeWithBottomTabsActivity::class.java))
            }else if (loginObject.get("response").equals(0)){
                progressDialog.hideProgress()
                Toast.makeText(context, loginObject.get("message").toString(), Toast.LENGTH_LONG).show()
            }

        }

    }
}