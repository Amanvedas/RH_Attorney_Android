package com.rafayee.RHAttorney

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

import java.text.SimpleDateFormat
import java.util.*

class SuccessDialogActivity : AppCompatActivity() {
    var stTime: String? = ""
    var ndTime: String? = ""
    var mnTime: String? = ""
    var name: String? = ""
    lateinit var startTime: TextView
    lateinit var endTime: TextView
    lateinit var userName: TextView
    lateinit var mainTime: TextView
    lateinit var close: ImageView
    lateinit var done: ImageView
    var attorneyName: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_dialog)
        supportActionBar?.hide()
        name = intent.getStringExtra("name")
        stTime = intent.getStringExtra("startTime")
        ndTime = intent.getStringExtra("endTime")
        attorneyName = intent.getStringExtra("AttorneyName")
        showCustomDialogSuccess()
    }

    private fun getDateTime(s: Long, e: Long) {
        var date: Date = Date(s)
        val sdfMain = SimpleDateFormat("EEEE, MMM dd, yyyy zzzz (ZZZZ)")
        sdfMain.timeZone = TimeZone.getTimeZone("US/Mountain")
        //   sdfMain.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        val netDateMain = Date(e)
        mainTime.text = sdfMain.format(netDateMain)
        Log.e("Asda", "asda" + date)
        val sdf = SimpleDateFormat("hh:mm a")
        val netDate = Date(e)

        // endTime.text=sdf.format(netDate)
        val sdf1 = SimpleDateFormat("hh:mm a")
        val netDate1 = Date(s)
        startTime.text = sdf1.format(netDate1)
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

    private fun showCustomDialogSuccess() {
        val dialog = Dialog(this@SuccessDialogActivity)
        dialog.setContentView(R.layout.appointment_success_response)
        dialog.setTitle("Title...")
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.attributes?.windowAnimations = R.anim.fade_in
        userName = dialog.findViewById(R.id.name)
        startTime = dialog.findViewById(R.id.startTime)
        endTime = dialog.findViewById(R.id.endTime)
        mainTime = dialog.findViewById(R.id.mainTime)
        done = dialog.findViewById(R.id.done)
        close = dialog.findViewById(R.id.close)
        userName.text = name + ", " + "We'have got you confirmed for your appointment"
        endTime.text = attorneyName

        getDateTime(stTime!!.toLong() * 1000, ndTime!!.toLong() * 1000)
        done.setOnClickListener {
            //close.performClick()
            // Change //startActivity(Intent(this@SuccessDialogActivity, MoreInfoActivity::class.java))
        }
        close.setOnClickListener {
            startActivity(Intent(this@SuccessDialogActivity, MainActivity::class.java))
        }
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        // set the custom dialog components - text, image and button
        dialog.show()
    }
}