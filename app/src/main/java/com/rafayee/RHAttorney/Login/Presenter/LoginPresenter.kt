package com.rafayee.RH.Login.Presenter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.biometric.BiometricPrompt
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rafayee.RH.Utils.PinInFiled
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rafayee.RH.Forgot.View.ForgotActivity
import com.rafayee.RH.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RH.Login.View.LoginActivity
import com.rafayee.RH.Login.View.LoginView
import com.rafayee.RH.Utils.FocusChangeListener
import com.rafayee.RH.Utils.PinFieldFocusChangeListener
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import java.util.*
import java.util.concurrent.Executor
import java.util.regex.Pattern

class LoginPresenter: RetrofitCallbacks.ServerResponseInterface {
    lateinit var context:Context
    lateinit var username: TextInputEditText
    lateinit var password: TextInputEditText
    private var loginView: LoginView? = null

    fun LoginInstance(
        context: Context,
        loginView: LoginView,
        username: TextInputEditText,
        password: TextInputEditText
    ){
        this.context=context
        this.username=username
        this.password=password
        this.loginView = loginView
    }

    fun validations(){
        if(username.text?.trim()?.isNotEmpty()!!){
            if(password.text?.trim()?.isNotEmpty()!!){
                showCustomDialogSuccess()
            }else{
                loginView?.notifyUser("Enter password")
            }
        }else{
            loginView?.notifyUser("Enter email/phone number")
        }
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
        //("Not yet implemented")
    }

    override fun successCallBack(body: String?, from: String?) {
        //("Not yet implemented")
    }
}