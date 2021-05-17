package com.rafayee.RH.Login.View

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.facebook.CallbackManager
import com.facebook.login.widget.LoginButton
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.rafayee.RH.Forgot.View.ForgotActivity

import com.rafayee.RH.Login.Presenter.LoginPresenter
import com.rafayee.RH.Utils.FocusChangeListener
import com.rafayee.RHAttorney.R
import java.util.*

class LoginActivity : AppCompatActivity(),LoginView {
    lateinit var forgot: TextView
    lateinit var back: ImageView
    lateinit var login: ImageView
    lateinit var email: TextInputEditText
    lateinit var pwd: TextInputEditText
    lateinit var googleBtn: ImageView
    lateinit var fingerPrint: ImageView
    lateinit var presenter: LoginPresenter
    lateinit var apple: ImageView
    lateinit var faceId: ImageView
    lateinit var cardEmail: TextInputLayout
    lateinit var cardPwd: TextInputLayout

    var firebaseAuth: FirebaseAuth? = null
    lateinit var loginButton: LoginButton
    lateinit var callbackManager: CallbackManager
    lateinit var signUpText: TextView
    var fb = false

    private lateinit var cancellationSignal: CancellationSignal
    val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("Authentication $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Authentication Success!")
                }
            }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        initVar()
        callbackManager = CallbackManager.Factory.create()
        presenter = LoginPresenter()
        presenter.LoginInstance(this,this@LoginActivity, email, pwd)


        FocusChangeListener(this, cardEmail, email, 0, 0, 10, 5, 5, 5, 10, 5)
        FocusChangeListener(this, cardPwd, pwd, 0, 0, 10, 5, 5, 5, 10, 5)

        checkBiometricSupport()
        fingerPrint.setOnClickListener {
            Log.e("asdasdasd", "assadasd");
            presenter.bottomSheet(this@LoginActivity, true)
        }
        faceId.setOnClickListener {
            presenter.bottomSheet(this@LoginActivity, false)
        }
        login.setOnClickListener {
            presenter.validations()
        }
      /*  signUpText.setOnClickListener {
          // Change //startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }*/
        forgot.setOnClickListener {
            startActivity(Intent(this, ForgotActivity::class.java))
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
       // googleBtn = findViewById(R.id.google_btn)
       // apple = findViewById(R.id.apple)
        faceId = findViewById(R.id.face_Id)
        //signUpText = findViewById(R.id.signUpText)
        cardEmail = findViewById(R.id.cardEmail)
        cardPwd = findViewById(R.id.cardPwd)
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

    fun checkBiometricSupport(): Boolean {
        val keyguardManager: KeyguardManager =
            getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isKeyguardSecure) {
            //notifyUser("Fingerprint not been enbled in settings")
            return false
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notifyUser("FingerPrint permisson is not been enabled")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            return true
        } else true
    }

    @SuppressLint("NewApi")
    override fun finger(context: Context) {
        val biometricPrompt = BiometricPrompt.Builder(context)
            .setTitle("Title of Prompr")
            .setSubtitle("Authentication required")
            .setDescription("This app uses fingerprint protection")
            .setNegativeButton("Cancel", context.mainExecutor,
                DialogInterface.OnClickListener { dialog, which ->
                    notifyUser("Authentication failed")
                }).build()

        biometricPrompt.authenticate(
            getCancellationSignal(),
            context.mainExecutor,
            authenticationCallback
        )
    }
    @SuppressLint("NewApi")
    override fun face(context: Context) {
        val biometricPrompt = BiometricPrompt.Builder(context)
            .setTitle("Title of Prompr")
            .setSubtitle("Authentication required")
            .setDescription("This app uses faceId protection")
            .setNegativeButton("Cancel", context.mainExecutor,
                DialogInterface.OnClickListener { dialog, which ->
                    notifyUser("Authentication failed")
                }).build()
        biometricPrompt.authenticate(
            getCancellationSignal(),
            context.mainExecutor,
            authenticationCallback
        )
    }
}
interface LoginView {
    fun finger(context: Context)
    fun face(context: Context)
    fun notifyUser(message: String)
}