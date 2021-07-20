package com.rafayee.RHAttorney.Login.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.facebook.CallbackManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rafayee.RHAttorney.Forgot.View.ForgotActivity
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.Login.Presenter.LoginPresenter
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.rafayee.RHAttorney.Utils.BiometricUtils.checkBiometricSupport
import com.rafayee.RHAttorney.Utils.BiometricUtils.fingerface
import com.rafayee.RHAttorney.Utils.FocusChangeListener

class LoginActivity : AppCompatActivity(),LoginView   {
    lateinit var forgot: TextView
    lateinit var back: ImageView
    lateinit var login: ImageView
    lateinit var email: TextInputEditText
    lateinit var pwd: TextInputEditText
    lateinit var btnRememberMe : SwitchCompat
    lateinit var fingerPrint: ImageView
    lateinit var presenter: LoginPresenter
    lateinit var faceId: ImageView
    lateinit var cardEmail: TextInputLayout
    lateinit var cardPwd: TextInputLayout
    lateinit var li_finger : LinearLayout
    lateinit var li_face : LinearLayout
    lateinit var callbackManager: CallbackManager
    var fb = false
    var finger:Boolean = false
    lateinit var cameraBottomSheetDialog : BottomSheetDialog

    private lateinit var cancellationSignal: CancellationSignal
    val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    //notifyUser("Authentication $errString")
                    presenter.showEnableSuccess(errString.toString(),finger)
                    //cameraBottomSheetDialog.dismiss()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Authentication Success!")
                    val pref = getSharedPreferences("LoginPref", 0) // 0 - for private mode
                    val editor = pref.edit()
                    if (finger) {
                        editor.putBoolean("enableTouchID", true)
                        editor.putBoolean("enableFaceID", false)
                    }else{
                        editor.putBoolean("enableTouchID", false)
                        editor.putBoolean("enableFaceID", true)
                    }
                    editor.apply()
                    ProgressDialog.getInstance().showProgress(this@LoginActivity)
                    RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
                    presenter.fetch(this@LoginActivity, pref.getString("emailID","").toString(), "fetch_bottom")
                    //cameraBottomSheetDialog.dismiss()
                }
            }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        initVar()
        val pref = getSharedPreferences("LoginPref", 0)
        callbackManager = CallbackManager.Factory.create()
        presenter = LoginPresenter()
         RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
        presenter.LoginInstance(this,this@LoginActivity, email, pwd,btnRememberMe)

        FocusChangeListener(this, cardEmail, email, 0, 0, 10, 5, 5, 5, 10, 5)
        FocusChangeListener(this, cardPwd, pwd, 0, 0, 10, 5, 5, 5, 10, 5)

        if (pref.getBoolean("isRemember",false)){
            email.setText(pref.getString("emailID","").toString())
            pwd.setText(pref.getString("password","").toString())
            email.requestFocus()
            pwd.requestFocus()
            btnRememberMe.isChecked = pref.getBoolean("isRemember",false)
        }else{
            email.setText("")
            pwd.setText("")
        }

        checkBiometricSupport(this@LoginActivity)
        checkBiometric()
        fingerPrint.setOnClickListener {
            Log.e("asdasdasd", "assadasd")
//            beforeClearFunctions()
            RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
            presenter.bottomSheet(this@LoginActivity, true)
        }
        li_finger.setOnClickListener {
            Log.e("asdasdasd", "assadasd")
//            beforeClearFunctions()
            RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
            presenter.bottomSheet(this@LoginActivity, true)
        }
        faceId.setOnClickListener {
//            beforeClearFunctions()
            RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
            presenter.bottomSheet(this@LoginActivity, false)
        }
        li_face.setOnClickListener {
//            beforeClearFunctions()
            RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
            presenter.bottomSheet(this@LoginActivity, false)
        }
        login.setOnClickListener {
            RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
            presenter.validations()
        }
        forgot.setOnClickListener {
            startActivity(Intent(this, ForgotActivity::class.java).putExtra("update","forgot"))
        }
        back.setOnClickListener {
            onBackPressed()
        }
    }

    fun initVar() {
        forgot = findViewById(R.id.forgot)
        back = findViewById(R.id.back)
        login = findViewById(R.id.login)
        email = findViewById(R.id.email)
        pwd = findViewById(R.id.pwd)
        fingerPrint = findViewById(R.id.fingerPrint)
        faceId = findViewById(R.id.face_Id)
        cardEmail = findViewById(R.id.cardEmail)
        btnRememberMe = findViewById(R.id.remember_switch)
        cardPwd = findViewById(R.id.cardPwd)
        li_finger = findViewById(R.id.li_finger)
        li_face = findViewById(R.id.li_face)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("asdsads", "sdadsa" + requestCode)
        if (requestCode == 100) {
            fb = false
        }
        if (fb) {
            fb = false
            callbackManager.onActivityResult(requestCode, resultCode, data)
        } else {
            fb = true
            if (requestCode == 100) {
                Log.e("asdsads", "asdasdsads" + requestCode)

            }
        }
    }

    override fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal.setOnCancelListener {
            notifyUser("Authentication is cancelled by user")
        }
        return cancellationSignal
    }

    fun checkBiometric() {
        if (fingerface.equals("finger")){
            li_finger.setVisibility(View.VISIBLE)
            li_face.setVisibility(View.GONE)
        }else if (fingerface.equals("face")){
            li_finger.setVisibility(View.GONE)
            li_face.setVisibility(View.VISIBLE)
        }else{
            li_finger.setVisibility(View.GONE)
            li_face.setVisibility(View.GONE)
        }
    }

    @SuppressLint("NewApi")
    override fun finger(context: Context, cameraBottomSheetDialog: BottomSheetDialog) {
        finger = true
        this.cameraBottomSheetDialog = cameraBottomSheetDialog
        val biometricPrompt = BiometricPrompt.Builder(context)
            .setTitle("Login to R&H\n")
            .setSubtitle("Touch ID")
            .setDescription("Touch the fingerprint sensor")
            .setNegativeButton("Cancel", context.mainExecutor,
                DialogInterface.OnClickListener { dialog, which ->
                    notifyUser("Authentication failed")
                    presenter.showEnableSuccess("Authentication failed",finger) }).build()

        biometricPrompt.authenticate(
            getCancellationSignal(),
            context.mainExecutor,
            authenticationCallback
        )
    }
    @SuppressLint("NewApi")
    override fun face(context: Context, cameraBottomSheetDialog: BottomSheetDialog) {
        finger = false
        this.cameraBottomSheetDialog = cameraBottomSheetDialog
        val biometricPrompt = BiometricPrompt.Builder(context)
            .setTitle("Login to R&H\n")
            .setSubtitle("Face ID")
            .setDescription("Look directly at your front camera")
            .setNegativeButton("Cancel", context.mainExecutor,
                DialogInterface.OnClickListener { dialog, which ->
                    notifyUser("Authentication failed")
                    presenter.showEnableSuccess("Authentication failed",finger)
                }).build()
        biometricPrompt.authenticate(
            getCancellationSignal(),
            context.mainExecutor,
            authenticationCallback
        )
    }
}
interface LoginView {
    fun finger(context: Context, cameraBottomSheetDialog: BottomSheetDialog)
    fun face(context: Context, cameraBottomSheetDialog: BottomSheetDialog)
    fun notifyUser(message: String)
}