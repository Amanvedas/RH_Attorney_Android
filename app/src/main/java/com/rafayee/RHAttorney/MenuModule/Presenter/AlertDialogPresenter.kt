package com.rafayee.RHAttorney.MenuModule.Presenter

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RHAttorney.Login.View.LoginActivity
import com.rafayee.RHAttorney.MenuModule.View.IUpdate
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
        })
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    fun filterAlertDialog() {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.filter_dialog_layout)
        // dialog.setTitle("Title...")
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val imgCanel: ImageView = dialog.findViewById(R.id.img_cancel)
        imgCanel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }


    fun calAlertDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.calender_alert_layout)
        // dialog.setTitle("Title...")
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val imgCanel: LinearLayout = dialog.findViewById(R.id.lay_back)
        val imgDone: ImageView = dialog.findViewById(R.id.img_done)
        imgCanel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        imgDone.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
           // iUpdate.finishView()
            context.startActivity(Intent(context, HomeWithBottomTabsActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))

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
            context.startActivity(Intent(context, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
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
        val imgSend: Button = dialog.findViewById(R.id.img_send)
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