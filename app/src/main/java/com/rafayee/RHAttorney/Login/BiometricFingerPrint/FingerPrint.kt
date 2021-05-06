package com.rafayee.RH.Login.BiometricFingerPrint

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity

public class FingerPrint : AppCompatActivity() {


/*
    private lateinit var cancellationSignal: CancellationSignal
    val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() = @RequiresApi(Build.VERSION_CODES.P) object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {

                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("Authentication $errString")

                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Authentication Success!")
                    //TODO startActivity(Intent(this@MainActivity,SecreatActivity::class.java))
                }
    }*/

    @RequiresApi(Build.VERSION_CODES.P)
    fun getIstance(context:Context, btn_auth:ImageView,authenticationCallback:BiometricPrompt.AuthenticationCallback, cancellationSignal:CancellationSignal){

        Log.e("sdasdsa","asfasdd")

        checkBiometricSupport()


        btn_auth.setOnClickListener {

            Log.e("sdasdsa","Asdsads")

            val biometricPrompt=BiometricPrompt.Builder(context)
                .setTitle("Title of Prompr")
                .setSubtitle("Authentication required")
                .setDescription("This app uses fingerprint protection")
                .setNegativeButton("Cancel",context.mainExecutor,
                    DialogInterface.OnClickListener{ dialog, which ->

                    notifyUser("Authentication failed")

                }).build()

            biometricPrompt.authenticate(getCancellationSignal(cancellationSignal),mainExecutor,authenticationCallback)

        }



    }


    fun checkBiometricSupport():Boolean{

        Log.e("sdasdsa","safsafdsad")

        val keyguardManager: KeyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if(!keyguardManager.isKeyguardSecure){
            notifyUser("Fingerprint not been enbled in settings")
            return false
        }

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED){
            notifyUser("FingerPrint permisson is not been enabled")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
            return true

        }else true
    }


    fun notifyUser(message:String){

        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }



    private fun getCancellationSignal(cancellationSignal: CancellationSignal): CancellationSignal {

        cancellationSignal!= CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication is cancelled by user")

        }
        return cancellationSignal!!
    }
}