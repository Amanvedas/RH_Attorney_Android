package com.rafayee.RHAttorney.MenuModule

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.rafayee.RHAttorney.R


class ContactUsActivity : AppCompatActivity() {
   lateinit var  rlBlurLayout : RelativeLayout
   lateinit var  img_call : ImageView
   lateinit var  img_back : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us_menu)
        supportActionBar?.hide()
        rlBlurLayout = findViewById(R.id.rl_blur)
        img_call = findViewById(R.id.img_call)
        img_back = findViewById(R.id.img_back)
        img_back.setOnClickListener { onBackPressed() }
        img_call.setOnClickListener { onCallBtnClick() }
    }

    private fun onCallBtnClick() {
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall()
        } else {
            if (ActivityCompat.checkSelfPermission(this@ContactUsActivity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                phoneCall()
            } else {
                val PERMISSIONS_STORAGE = arrayOf<String>(Manifest.permission.CALL_PHONE)
                //Asking request Permissions
                ActivityCompat.requestPermissions(this@ContactUsActivity, PERMISSIONS_STORAGE, 9)
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        var permissionGranted = false
        when (requestCode) {
            9 -> permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        if (permissionGranted) {
            phoneCall()
        } else {
            Toast.makeText(this@ContactUsActivity, "You don't assign permission.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun phoneCall() {
        if (ActivityCompat.checkSelfPermission(this@ContactUsActivity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:12345678900")
            startActivity(callIntent)
        } else {
            Toast.makeText(this@ContactUsActivity, "You don't assign permission.", Toast.LENGTH_SHORT).show()
        }
    }
}