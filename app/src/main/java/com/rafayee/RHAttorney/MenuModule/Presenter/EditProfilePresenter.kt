package com.rafayee.RH.MenuModule.Presenter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.rafayee.RHAttorney.MenuModule.ProfileActivity
import java.util.regex.Pattern

class EditProfilePresenter {
    lateinit var context: Context
    lateinit var edtEmailPresenter: TextInputEditText
    lateinit var edtPhoneNumberPresenter: TextInputEditText
    lateinit var edtStatePresenter: TextInputEditText
    lateinit var edtCityPresenter: TextInputEditText
    lateinit var edtZipCodePresenter: TextInputEditText
    lateinit var edtStreetPresenter: TextInputEditText
    lateinit var edtFirstNamePresenter: TextInputEditText
    lateinit var edtLastNamePresenter: TextInputEditText
    lateinit var btnUpdate: AppCompatButton

    fun EditProfilePresenter(
        context: Context,
        edtFirstNamePresenter: TextInputEditText,
        edtLastNamePresenter: TextInputEditText,
        edtEmailPresenter: TextInputEditText,
        edtPhoneNumberPresenter: TextInputEditText,
        edtStatePresenter: TextInputEditText,
        edtCityPresenter: TextInputEditText,
        edtZipCodePresenter: TextInputEditText,
        edtStreetPresenter: TextInputEditText,
        btnUpdate: AppCompatButton
    ) {
        this.context = context
        this.edtEmailPresenter = edtEmailPresenter
        this.edtPhoneNumberPresenter = edtPhoneNumberPresenter
        this.edtStatePresenter = edtStatePresenter
        this.edtCityPresenter = edtCityPresenter
        this.edtZipCodePresenter = edtZipCodePresenter
        this.edtStreetPresenter = edtStreetPresenter
        this.edtFirstNamePresenter = edtFirstNamePresenter
        this.edtLastNamePresenter = edtLastNamePresenter
        this.btnUpdate = btnUpdate
    }

    fun validation() {
        if (edtFirstNamePresenter.text.toString().isEmpty()) {
            edtFirstNamePresenter.requestFocus()
            Toast.makeText(context, "Enter First Name", Toast.LENGTH_SHORT).show()
        } else if (edtLastNamePresenter.text.toString().isEmpty()) {
            edtLastNamePresenter.requestFocus()
            Toast.makeText(context, "Enter Last Name", Toast.LENGTH_SHORT).show()
        } else if (edtEmailPresenter.text.toString().isEmpty()) {
            edtEmailPresenter.requestFocus()
            Toast.makeText(context, "Enter Email", Toast.LENGTH_SHORT).show()
        } else if (!validEmail(edtEmailPresenter.text.toString())) {
            edtEmailPresenter.requestFocus()
            Toast.makeText(context, "Enter valid email", Toast.LENGTH_SHORT).show()
        } else if (edtPhoneNumberPresenter.text.toString().isEmpty()) {
            edtPhoneNumberPresenter.requestFocus()
            Toast.makeText(context, "Enter Phone Number", Toast.LENGTH_SHORT).show()
        } else if (edtStreetPresenter.text.toString().isEmpty()) {
            edtStreetPresenter.requestFocus()
            Toast.makeText(context, "Enter Street", Toast.LENGTH_SHORT).show()
        } else if (edtCityPresenter.text.toString().isEmpty()) {
            edtCityPresenter.requestFocus()
            Toast.makeText(context, "Enter City", Toast.LENGTH_SHORT).show()
        } else if (edtStatePresenter.text.toString().isEmpty()) {
            edtStatePresenter.requestFocus()
            Toast.makeText(context, "Enter State", Toast.LENGTH_SHORT).show()
        } else if (edtZipCodePresenter.text.toString().isEmpty()) {
            edtZipCodePresenter.requestFocus()
            Toast.makeText(context, "Enter Zip Code", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Profile is updated", Toast.LENGTH_SHORT).show()
            context.startActivity(Intent(context, ProfileActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }
    private fun validEmail(target: String?): Boolean {
        //val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val emailPattern = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}"
        val pattern = Pattern.compile(emailPattern)
        return !TextUtils.isEmpty(target) && pattern.matcher(target).matches()
    }
}