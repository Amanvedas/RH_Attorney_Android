package com.rafayee.RH.Login.View

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import com.facebook.CallbackManager
import com.facebook.login.widget.LoginButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.rafayee.RH.Forgot.View.ForgotActivity
import com.rafayee.RH.Login.Presenter.LoginPresenter
import com.rafayee.RH.Utils.FocusChangeListener
import com.rafayee.RHAttorney.MyService
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks

class LoginActivity : AppCompatActivity(),LoginView   {
    lateinit var forgot: TextView
    lateinit var back: ImageView
    lateinit var login: ImageView
    lateinit var email: TextInputEditText
    lateinit var pwd: TextInputEditText
    lateinit var btnRememberMe : SwitchCompat
    lateinit var googleBtn: ImageView
    lateinit var fingerPrint: ImageView
    lateinit var presenter: LoginPresenter
    lateinit var apple: ImageView
    lateinit var faceId: ImageView
    lateinit var service : MyService
    lateinit var id : String
    lateinit var tokenF : String
    lateinit var cardEmail: TextInputLayout
    lateinit var cardPwd: TextInputLayout
    var filename = "Valustoringfile"
    var preferID = "TokenID"
    var SP: SharedPreferences? = null
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

/*
        FirebaseInstanceId.getInstance().getInstanceId()
            .addOnCompleteListener(object : WebDialog.OnCompleteListener<InstanceIdResult?>() {
                fun onComplete(task: Task<InstanceIdResult>) {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException())
                        return
                    }

                    // Get new Instance ID token
                    val token: String = task.getResult().getToken()
                }
            })
*/
        callbackManager = CallbackManager.Factory.create()
        presenter = LoginPresenter()
         RetrofitCallbacks.getInstace().initializeServerInterface(presenter)

        tokenF="token"
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
            if (task.isSuccessful){
                print(task.result?.token)
                Log.e("fire","token:: "+task.result?.token)
                tokenF = task.result?.token.toString()
            }
            else{
                print(task.exception?.message)
                Log.e("fire","error:: "+task.exception?.message)

            }
        }
        Log.e("tokenRa",":: "+tokenF)
        presenter.LoginInstance(this,this@LoginActivity, email, pwd,id,tokenF,btnRememberMe)

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
       // googleBtn = findViewById(R.id.google_btn)
       // apple = findViewById(R.id.apple)
        faceId = findViewById(R.id.face_Id)
        //signUpText = findViewById(R.id.signUpText)
        cardEmail = findViewById(R.id.cardEmail)
        btnRememberMe = findViewById(R.id.remember_switch)
        cardPwd = findViewById(R.id.cardPwd)

        id  = Settings.Secure.getString(contentResolver,Settings.Secure.ANDROID_ID)
        Log.e("idd","is:: "+id)

        btnRememberMe.setOnClickListener(View.OnClickListener {
            if (btnRememberMe.isChecked){
                SP = getSharedPreferences(preferID, 0)
                if(email.text.isNullOrBlank()){
                    if(pwd.text.isNullOrBlank()){
                        val getname: String? = SP!!.getString("key1", "")
                        val getpass: String? = SP!!.getString("key2", "")
                        email.setText(getname)
                        pwd.setText(getpass)
                    }


                }


            }

        })


     //   val token = FirebaseInstanceId.getInstance().token

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(object : OnCompleteListener<InstanceIdResult?> {
                override fun onComplete(@NonNull task: Task<InstanceIdResult?>) {
                    if (!task.isSuccessful()) {
                        Log.e("TAG", "getInstanceId failed"+task.getException())
                        return
                    }

                    // Get new Instance ID token
                    val token: String = task.getResult()!!.getToken()
                    Log.e("firebaseTokenInLogin", ":= "+token)

                    // Log and toast
                    val msg = getString(R.string.messenger_send_button_text, token)
                    Log.e("lldld", ":= "+msg)
                   // Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
                }
            })

/*
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isComplete){
               var firebaseToken : String = it.result.toString()
                Log.e("tokennn","is:: "+id+", "+firebaseToken)

            }
        }
*/
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