package com.rafayee.RHAttorney.Login.Presenter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.provider.Settings
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
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rafayee.RHAttorney.Forgot.View.ForgotActivity
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RHAttorney.Login.Model.LoginResponseController
import com.rafayee.RHAttorney.Login.Model.LoginResponseModel
import com.rafayee.RHAttorney.Login.View.LoginView
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.rafayee.RHAttorney.ServerConnections.SessionManager
import com.rafayee.RHAttorney.Utils.FocusChangeListener
import com.vedas.apna.ServerConnections.AppStatus
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.regex.Pattern


class LoginPresenter: RetrofitCallbacks.ServerResponseInterface {
    lateinit var context:Context
    lateinit var rememberSwitch : SwitchCompat
    lateinit var username: TextInputEditText
    lateinit var password: TextInputEditText
    private var loginView: LoginView? = null

    var isLogin: Boolean = false
    var cameraBottomSheetDialog: BottomSheetDialog? = null
    var pwd: TextInputEditText? = null
    var email: TextInputEditText? = null
    var editTextLay: LinearLayout? = null
    var fingerPrintLay: LinearLayout? = null
    var textSentence: TextView? = null
    var idName: TextView? = null
    var hintTextSensor: TextView? = null
    var topImage: ImageView? = null
    var bottomImage: ImageView? = null
    var finger: Boolean? = null


    fun LoginInstance(
        context: Context,
        loginView: LoginView,
        username: TextInputEditText,
        password: TextInputEditText,
        rememberSwitch: SwitchCompat
    ){
        this.context=context
        this.username=username
        this.password=password
        this.loginView = loginView
        this.rememberSwitch = rememberSwitch
    }

    fun validations(){
        if (username.text?.trim()?.isNotEmpty()!!) {
            if(username.text.toString().contains("@")){
                if (validEmail(username.text.toString())) {
                    nextFunctions()
                } else {
                    username.requestFocus()
                    loginView?.notifyUser("Enter valid email")
                }
            }else{
                if (isValidPhoneallzeros(username.text.toString())) {
                    nextFunctions()
                } else {
                    username.requestFocus()
                    loginView?.notifyUser("Phone number must have 10 digits(all zero's doesn't allow)")
                }
            }
        } else {
            username.requestFocus()
            loginView?.notifyUser("Enter email/phone number")
        }
    }
    private fun nextFunctions() {
        if (password.text?.trim()?.isNotEmpty()!!) {
            if (isValidPassword(password.text.toString().trim())){
                if (AppStatus.getInstance(context).isConnected()) {
                    ProgressDialog.getInstance().showProgress(context)
                    Handler().postDelayed({
                        loginApi(
                            context,
                            username.text.toString(),
                            password.text.toString(),
                            "login"
                        )
                    }, 1000)
                } else {
                    Toast.makeText(context, "No Internet Connection!!!!", Toast.LENGTH_SHORT).show()
                }
                //showCustomDialogSuccess()
            } else {
                password.requestFocus()
                loginView?.notifyUser("Password length must be minimum 8 and maximum 13 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number, 1 Special Character and Spaces not allowed.")
            }
        } else {
            password.requestFocus()
            loginView?.notifyUser("Enter password")
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
        //RetrofitCallbacks.getInstace().OTPApiCall(context,otpObject)
        RetrofitCallbacks.getInstace().apiCallBacks(context,"attorney/login",otpObject,"login_bottom")
    }

    fun isValidPassword(password: String?): Boolean {
        val expression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@!%*?&#])(?=\\S+$)[A-Za-z\\d$@!%*?&#]{8,13}"
        val pattern = Pattern.compile(expression)
        return !TextUtils.isEmpty(password) && pattern.matcher(password).matches()
    }
    public fun loginApi(context: Context, usrname: String, password: String, from: String) {
        var loginObj = JsonObject()
        val jsonObject = JSONObject()

        val token = FirebaseInstanceId.getInstance().token
        Log.e("firebaseTokenInLogin", ":=$token")
        val ID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
        try {
            jsonObject.put("emailID", usrname)
            jsonObject.put("password", password)
            jsonObject.put("deviceID", ID)
            jsonObject.put("deviceToken", token)
            jsonObject.put("deviceType", "mobile")
            val jsonParser = JsonParser()
            loginObj = jsonParser.parse(jsonObject.toString()) as JsonObject

            //print parameter
            Log.e("LOGINJSONss:", " $loginObj")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        RetrofitCallbacks.getInstace().apiCallBacks(context,"attorney/login",loginObj,from)
    }

    fun fetch(context: Context, Email: String, from: String) {
        this.context = context
        var regObj = JsonObject()
        val jsonObject = JSONObject()

        try {
            jsonObject.put("emailID", Email)
            val jsonParser = JsonParser()
            regObj = jsonParser.parse(jsonObject.toString()) as JsonObject
            //print parameter
            Log.e("RegisterJSON:", " $regObj")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        RetrofitCallbacks.getInstace().apiCallBacks(context, "attorney/FetchAttorney", regObj, from)
    }

    fun showEnableSuccess(error: String, finger: Boolean) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.enable_ids_dialog)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        var dontAllow: ImageView
        var ok: ImageView
        var headingText: TextView
        var li_error: LinearLayout

        dontAllow = dialog.findViewById(R.id.dontAllow)
        ok = dialog.findViewById(R.id.ok)
        headingText = dialog.findViewById(R.id.heading)
        li_error = dialog.findViewById(R.id.li_error)
        li_error.visibility = View.VISIBLE
        dontAllow.setImageDrawable(context.resources.getDrawable(R.drawable.cancel_btn))
        ok.setImageDrawable(context.resources.getDrawable(R.drawable.tryagain))
        headingText.text = error

        dontAllow.setOnClickListener {
            dialog.dismiss()
            cameraBottomSheetDialog?.dismiss()
        }

        ok.setOnClickListener {
            dialog.dismiss()
            if (finger) {
                loginView?.finger(context, cameraBottomSheetDialog!!)
            } else {
                loginView?.face(context, cameraBottomSheetDialog!!)
            }
        }
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    fun bottomSheet(context: Context, finger: Boolean) {
        this.finger = finger
        cameraBottomSheetDialog =
            BottomSheetDialog(Objects.requireNonNull(context), R.style.BottomSheetDialogTheme)
        val dialogView: View =
            LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null)
        cameraBottomSheetDialog!!.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        hintTextSensor = dialogView.findViewById(R.id.hintTextSensor)
        idName = dialogView.findViewById(R.id.idName)
        bottomImage = dialogView.findViewById(R.id.bottomImage)
        val back: ImageView = dialogView.findViewById(R.id.back)
        val login: ImageView = dialogView.findViewById(R.id.login)
        editTextLay = dialogView.findViewById(R.id.editTextLay)
        fingerPrintLay = dialogView.findViewById(R.id.fingerPrintLay)
        textSentence = dialogView.findViewById(R.id.textSentence)
        val forgot: TextView = dialogView.findViewById(R.id.forgot)
        topImage = dialogView.findViewById(R.id.topImage)
        val cardEmail: TextInputLayout = dialogView.findViewById(R.id.cardEmail)
        val cardPwd: TextInputLayout = dialogView.findViewById(R.id.cardPwd)
        email = dialogView.findViewById(R.id.email)
        pwd = dialogView.findViewById(R.id.pwd)

        FocusChangeListener(context, cardEmail, email!!, 0, 0, 10, 5, 5, 5, 10, 5)
        FocusChangeListener(context, cardPwd, pwd!!, 0, 0, 10, 5, 5, 5, 10, 5)

        /*if (finger) {
            idName?.text = "Touch ID"
            hintTextSensor?.text = "Touch the fingerprint sensor"
            textSentence?.text = "To enable Touch ID please\nfirst login to R&H"
            topImage?.setImageDrawable(context.resources.getDrawable(R.drawable.fingerprint_2))
            bottomImage?.setImageDrawable(context.resources.getDrawable(R.drawable.fingerprint_3))
        } else {
            idName?.text = "Face ID"
            hintTextSensor?.text = "Look directly at your front camera"
            textSentence?.text = "To enable Face ID please\nfirst login to R&H"
            topImage?.setImageDrawable(context.resources.getDrawable(R.drawable.face_id_2))
            bottomImage?.setImageDrawable(context.resources.getDrawable(R.drawable.face_id_3))
        }*/
        back.setOnClickListener { cameraBottomSheetDialog!!.dismiss() }
        forgot.setOnClickListener {
            cameraBottomSheetDialog!!.dismiss()
            context.startActivity(Intent(context, ForgotActivity::class.java))
        }
        if (isLogin) {
            if (finger == true) {
                /*editTextLay?.visibility = View.GONE
                fingerPrintLay?.visibility = View.VISIBLE
                textSentence?.text = "Login to R&H"
                idName?.text = "Touch ID"
                hintTextSensor?.text = "Touch the fingerprint sensor"
                topImage?.setImageDrawable(context.resources.getDrawable(R.drawable.fingerprint_2))
                bottomImage?.setImageDrawable(context.resources.getDrawable(R.drawable.fingerprint_3))*/
                cameraBottomSheetDialog!!.dismiss()
                loginView?.finger(context, cameraBottomSheetDialog!!)
            } else {
                /*editTextLay?.visibility = View.GONE
                fingerPrintLay?.visibility = View.VISIBLE
                textSentence?.text = "Login to R&H"
                idName?.text = "Face ID"
                hintTextSensor?.text = "Look directly at your front camera"
                topImage?.setImageDrawable(context.resources.getDrawable(R.drawable.face_id_2))
                bottomImage?.setImageDrawable(context.resources.getDrawable(R.drawable.face_id_3))*/
                cameraBottomSheetDialog!!.dismiss()
                loginView?.face(context, cameraBottomSheetDialog!!)
            }
        }else{
            if (finger) {
                idName?.text = "Touch ID"
                hintTextSensor?.text = "Touch the fingerprint sensor"
                textSentence?.text = "To enable Touch ID please\nfirst login to R&H"
                topImage?.setImageDrawable(context.resources.getDrawable(R.drawable.fingerprint_2))
                bottomImage?.setImageDrawable(context.resources.getDrawable(R.drawable.fingerprint_3))
            } else {
                idName?.text = "Face ID"
                hintTextSensor?.text = "Look directly at your front camera"
                textSentence?.text = "To enable Face ID please\nfirst login to R&H"
                topImage?.setImageDrawable(context.resources.getDrawable(R.drawable.face_id_2))
                bottomImage?.setImageDrawable(context.resources.getDrawable(R.drawable.face_id_3))
            }
            if (context.getSharedPreferences("LoginPref", 0).getBoolean("isRemember", false)) {
                email!!.setText(context.getSharedPreferences("LoginPref", 0).getString("emailID", ""))
                pwd!!.setText(context.getSharedPreferences("LoginPref", 0).getString("password", ""))
                email!!.requestFocus()
                pwd!!.requestFocus()
            }

            login.setOnClickListener {
                if (email!!.text?.trim()?.isNotEmpty()!!) {
                    if (validEmail(email!!.text.toString())) {
                        if (pwd!!.text?.trim()?.isNotEmpty()!!) {
                            if (isValidPassword(pwd!!.text.toString())){
                                if (AppStatus.getInstance(context).isConnected()) {
                                    ProgressDialog.getInstance().showProgress(context)
                                    Handler().postDelayed({
                                        loginApi(
                                            context,
                                            email!!.text.toString(),
                                            pwd!!.text.toString(),
                                            "login_bottom"
                                        )
                                    }, 1000)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "No Internet Connection!!!!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                pwd!!.requestFocus()
                                Toast.makeText(
                                    context,
                                    "Password length must be minimum 8 and maximum 13 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number, 1 Special Character and Spaces not allowed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            pwd!!.requestFocus()
                            Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        email!!.requestFocus()
                        Toast.makeText(context, "Enter valid email", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    email!!.requestFocus()
                    Toast.makeText(context, "Enter email", Toast.LENGTH_SHORT).show()
                }
            }
            cameraBottomSheetDialog!!.setContentView(dialogView)
            cameraBottomSheetDialog!!.show()
        }

    }

    private fun validEmail(target: String?): Boolean {
        //val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val emailPattern = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}"
        val pattern = Pattern.compile(emailPattern)
        return !TextUtils.isEmpty(target) && pattern.matcher(target).matches()
    }

    override fun failureCallBack(failureMsg: String?) {
        ProgressDialog.getInstance().hideProgress()
        Toast.makeText(context,failureMsg,Toast.LENGTH_SHORT).show()
    }
    fun isValidPhoneallzeros(phone: String?): Boolean {
        val expression = "^(?!0+$)\\d*$"  /*"^(?!0+$)\\d{10,}$"*/
        val pattern = Pattern.compile(expression)
        return !TextUtils.isEmpty(phone)
                && pattern.matcher(phone).matches()
    }
    override fun successCallBack(body: String?, from: String?) {
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(body)
            if (jsonObject.getString("response").equals("3")) {
                if (from.equals("login") || from.equals("login_bottom")) {
                    if(from.equals("login_bottom")) {
                        val pref = context.getSharedPreferences("LoginPref", 0) // 0 - for private mode
                        val editor = pref.edit()
                        editor.putString("accesstoken", jsonObject.getString("access_token"))
                        editor.putString("password", pwd?.text.toString())
                        editor.putString("emailID", jsonObject.getJSONObject("clientInfo").getString("emailID"))
                        editor.apply()
                        isLogin = true
                        if (finger == true) {
                            cameraBottomSheetDialog!!.dismiss()
                            loginView?.finger(context, cameraBottomSheetDialog!!)
                        } else {
                            cameraBottomSheetDialog!!.dismiss()
                            loginView?.face(context, cameraBottomSheetDialog!!)
                        }
                        ProgressDialog.getInstance().hideProgress()
                    }else{
                        val pref = context.getSharedPreferences("LoginPref", 0) // 0 - for private mode
                        val editor = pref.edit()
                        editor.putString("accesstoken", jsonObject.getString("access_token"))
                        editor.putString("password", password.text.toString())
                        editor.putString("emailID", jsonObject.getJSONObject("clientInfo").getString("emailID"))
                        editor.apply()
                        fetch(context, jsonObject.getJSONObject("clientInfo").getString("emailID").toString(), "fetch")
                    }
                }else if (from.equals("fetch_bottom")){
                    val gson = Gson()
                    val loginResponseModel: LoginResponseModel = gson.fromJson(body, LoginResponseModel::class.java)
                    LoginResponseController.instance!!.loginResponseModel = loginResponseModel
                    Log.e("data","res:: "+gson.toJson(LoginResponseController.myObj?.loginResponseModel))

                    val sessionManager = SessionManager(context)
                    sessionManager.createLoginSession(
                        LoginResponseController.instance!!.loginResponseModel?.attorneysList?.get(0)?.emailID
                    )
                    val pref = context.getSharedPreferences("LoginPref", 0) // 0 - for private mode
                    val editor = pref.edit()
                    LoginResponseController.myObj?.loginResponseModel?.attorneysList?.get(0)?.password = password.text.toString()
                    editor.putString("password", pwd?.text.toString())
                    editor.putBoolean("isRemember", rememberSwitch.isChecked)
                    editor.putString("register_type", "Manual")
                    editor.putString("userInfo", Gson().toJson(LoginResponseController.myObj?.loginResponseModel))
                    editor.putString("emailID", LoginResponseController.instance!!.loginResponseModel?.attorneysList?.get(0)?.emailID)
                    editor.apply()
                    context.startActivity(Intent(context, HomeWithBottomTabsActivity::class.java))
                    ProgressDialog.getInstance().hideProgress()
                }else if (from.equals("fetch")){
                    val gson = Gson()
                    val loginResponseModel = gson.fromJson(body, LoginResponseModel::class.java)
                    LoginResponseController.instance!!.loginResponseModel = loginResponseModel
                    Log.e("data","res:: "+gson.toJson(LoginResponseController.myObj?.loginResponseModel))

                    val sessionManager = SessionManager(context)
                    sessionManager.createLoginSession(LoginResponseController.instance!!.loginResponseModel?.attorneysList?.get(0)?.emailID)
                    val pref = context.getSharedPreferences("LoginPref", 0) // 0 - for private mode
                    val editor = pref.edit()
                    LoginResponseController.myObj?.loginResponseModel?.attorneysList?.get(0)?.password = password.text.toString()
                    editor.putString("password", password.text.toString())
                    editor.putBoolean("isRemember", rememberSwitch.isChecked)
                    editor.putString("register_type", "Manual")
                    editor.putString("userInfo", Gson().toJson(LoginResponseController.myObj?.loginResponseModel))
                    editor.putString("emailID", LoginResponseController.instance!!.loginResponseModel?.attorneysList?.get(0)?.emailID)

                    editor.putBoolean("enableTouchID", false)
                    editor.putBoolean("enableFaceID", false)
                    editor.apply()
                    context.startActivity(Intent(context, HomeWithBottomTabsActivity::class.java))
                    ProgressDialog.getInstance().hideProgress()
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}