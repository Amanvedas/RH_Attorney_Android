package com.rafayee.RH.MenuModule.Presenter

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.rafayee.RH.MenuModule.PasswordUpdateSuccessfully
import com.rafayee.RH.MenuModule.View.IUpdate
import com.rafayee.RH.MenuModule.View.UpdatePasswordActivity

class UpdatePasswordPresenter {
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

    fun validations(){
        if(passwordOld.text?.trim()?.isNotEmpty()!!){
            if(passwordNew.text?.trim()?.isNotEmpty()!!){
                if (passwordConform.text?.trim()?.isNotEmpty()!!){
                    if (passwordNew.text.toString().equals(passwordConform.text.toString())){
                        iUpdate.finishView()
                        //Toast.makeText(context,"Password updated successfully!!!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context,"Password mismatched", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context,"Enter conform password", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context,"Enter new password", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(context,"Enter old password", Toast.LENGTH_SHORT).show()
        }
    }
}