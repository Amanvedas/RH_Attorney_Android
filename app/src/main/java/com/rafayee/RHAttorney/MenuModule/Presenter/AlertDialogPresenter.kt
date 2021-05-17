package com.rafayee.RH.MenuModule.Presenter

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rafayee.RH.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RH.Login.View.LoginActivity
import com.rafayee.RH.MenuModule.View.IUpdate
import com.rafayee.RHAttorney.MainActivity
import com.rafayee.RHAttorney.R


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class AlertDialogPresenter {
    lateinit var context: Context
    lateinit var iUpdate: IUpdate

    fun AlertDialogPresenter(context: Context) {
        this.context = context

    }

    fun logoutAlertDialogg(iUpdate: IUpdate) {
        this.iUpdate =iUpdate
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.logout_layout)
        // dialog.setTitle("Title...")
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val imgCanel: Button = dialog.findViewById(R.id.img_cancel)
        val imgSignout: TextView = dialog.findViewById(R.id.img_sign_out)
        imgCanel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        imgSignout.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            iUpdate.finishView()
            context.startActivity(Intent(context, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))

        })
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }
    fun logoutAlertDialog() {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.logout_layout)
        // dialog.setTitle("Title...")
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val imgCanel: Button = dialog.findViewById(R.id.img_cancel)
        val imgSignout: Button = dialog.findViewById(R.id.img_sign_out)
        imgCanel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        imgSignout.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            context.startActivity(Intent(context, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
        })
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    fun verificationAlert(strTxt: String, strTitle: String) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.verification_alertbox)
        // dialog.setTitle("Title...")
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val imgOk: ImageView = dialog.findViewById(R.id.img_ok)
        val txtDisc: TextView = dialog.findViewById(R.id.txt_title)
        val txtTitle: TextView = dialog.findViewById(R.id.txt_verify)
        txtTitle.setText(strTitle)
        txtDisc.setText(strTxt)
        imgOk.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    fun edtProfileAlert(){
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.message_to_layout)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val imgSend: ImageView = dialog.findViewById(R.id.img_send)
        val imgCancel: ImageView = dialog.findViewById(R.id.img_cancel)

        imgSend.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                context,
                "Profile Updated Successfully",
                Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        })
        imgCancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
      //  dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    fun techSupportAlert() {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.tech_support_alert)
        // dialog.setTitle("Title...")
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val imgTechSupport: ImageView = dialog.findViewById(R.id.img_tech)
        val imgCancel: ImageView = dialog.findViewById(R.id.img_cancel)
        imgTechSupport.setOnClickListener(View.OnClickListener {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !== PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CALL_PHONE), 0)
            } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                dialog.dismiss()
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:" + 3036880944)
                context.startActivity(callIntent)
            } else {
                Toast.makeText(context, "Allow Your Phone Permission", Toast.LENGTH_SHORT).show()
            }
        })
        imgCancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }
}