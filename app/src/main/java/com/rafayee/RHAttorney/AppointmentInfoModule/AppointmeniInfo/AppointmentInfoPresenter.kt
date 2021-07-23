package com.rafayee.RHAttorney.AppointmentInfoModule.AppointmeniInfo

import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class AppointmentInfoPresenter: RetrofitCallbacks.ServerResponseInterface {
    lateinit  var img_profile:CircleImageView
    lateinit var txt_name:TextView
    lateinit var txt_job:TextView
    lateinit var txt_disc:TextView
    lateinit var txt_estimate:TextView
    lateinit var txt_time:TextView
    lateinit var txt_case_disc:TextView
    lateinit var txt_hope_disc:TextView
    lateinit var context: Context

    fun AppointmentInfoPresenter(context: Context, img_profile:CircleImageView,
                                 txt_name:TextView,
                                 txt_job:TextView,
                                 txt_disc:TextView,
                                 txt_estimate:TextView,
                                 txt_time:TextView,
                                 txt_case_disc:TextView,
                                 txt_hope_disc:TextView, ) {
        this.context = context
        this.img_profile = img_profile
        this.txt_name = txt_name
        this.txt_job=txt_job
        this.txt_disc = txt_disc
        this.txt_estimate = txt_estimate
        this.txt_time = txt_time
        this.txt_case_disc = txt_case_disc
        this.txt_hope_disc = txt_hope_disc


    }

    fun appointmentInfoApi(context: Context, email:String){
        ProgressDialog.getInstance().showProgress(context)
        var emialObject: JsonObject = JsonObject()
        val jsonObject = JSONObject()

        try {
            jsonObject.put("emailID", email)
            val jsonParser = JsonParser()
            emialObject = jsonParser.parse(jsonObject.toString()) as JsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        RetrofitCallbacks.getInstace().apiCallBacks(context,"attorney/FetchAttorney",emialObject,"AppointmentInfo")
    }

    override fun successCallBack(body: String?, from: String?) {
        ProgressDialog.getInstance().hideProgress()
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(body)
            if (jsonObject.getString("response").equals("3")) {
                var jsonarray: JSONArray
                var jsonobj:JSONObject
                var jsonuserinfo:JSONObject
                jsonarray=   jsonObject.getJSONArray("attorneysList")
                jsonobj = jsonarray.getJSONObject(0)
                txt_name.text=jsonobj.getString("firstName")+" "+jsonobj.getString("lastName")
                txt_disc.text=jsonobj.getString("phoneNumber")
                txt_job.text=jsonobj.getString("emailID")
                txt_time.text=jsonobj.getString("register_time")
                //txt_case_disc.text=jsonobj.getString("firstName")
               // txt_hope_disc.text=jsonobj.getString("firstName")
                Glide.with(context)
                    .load(ServerApiCollection.IMAGE_URL+ jsonobj.getString("profilePic"))
                    .placeholder(R.drawable.profile_ic)
                    .into(img_profile)


            }
        }catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    override fun failureCallBack(failureMsg: String?) {
        ProgressDialog.getInstance().hideProgress()
        Log.e("Working","no:: ")
    }

}