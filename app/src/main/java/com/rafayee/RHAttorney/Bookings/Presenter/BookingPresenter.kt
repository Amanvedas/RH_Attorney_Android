package com.rafayee.RHAttorney.Presenter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rafayee.RHAttorney.Bookings.Adapter.AvailableTimeSlotAdapter
import com.rafayee.RHAttorney.Bookings.View.BookingActivity
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.HomeFragmentModule.Model.TimeSlots
import com.rafayee.RHAttorney.Model.SingleAttorneyResponse
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.vedas.apna.ServerConnections.AppStatus
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BookingPresenter: RetrofitCallbacks.ServerResponseInterface {
    lateinit var context: Context
    lateinit var amRecyclerView: RecyclerView
    lateinit var pmRecyclerView: RecyclerView
    var timeStamp:Long?=null
    val adapter = AvailableTimeSlotAdapter()
    val adapter1 = AvailableTimeSlotAdapter()
    lateinit var booking:CardView
    lateinit var cnfImg:ImageView
    lateinit var cardCnf:CardView

    fun getInstance(context: Context, amRecyclerView: RecyclerView, pmRecyclerView: RecyclerView,timeStamp:Long,booking:CardView, cnfImg:ImageView) {
        this.context = context
        this.amRecyclerView=amRecyclerView
        this.pmRecyclerView=pmRecyclerView
        this.timeStamp=timeStamp
        this.booking=booking
        this.cnfImg=cnfImg
    }
    fun availableSlots() {
        ProgressDialog.getInstance().hideProgress()
/*
        val adapter = AvailableTimeSlotAdapter()
        val adapter1 = AvailableTimeSlotAdapter()
*/
        Log.e("sdsads","SAdas"+timeStamp)
       val allTimeSlots:ArrayList<TimeSlots> = SingleAttorneyResponse.instance?.attorneysAvailableTimeList?.time_slots!!
        val amTimeSlots:ArrayList<TimeSlots>?= ArrayList()
        val pmTimeSlots:ArrayList<TimeSlots>?=ArrayList()
        for(i in allTimeSlots){
            Log.e("sads","ass"+i.start)
          val meridium:String= getDateTime(i.start?.toLong()!!*1000).toUpperCase(Locale.getDefault())
            Log.e("sdfsjfjklasn",meridium)
            if(meridium.equals("AM")){
                Log.e("sddsadsa","asdsadasds");
                amTimeSlots?.add(i)
                adapter.AvailableTimeSlotAdapter(context, amTimeSlots!!,timeStamp!!,this@BookingPresenter)
                amRecyclerView.adapter=adapter
            }else{
                Log.e("sddsadsa","asdsadasds1");
                   pmTimeSlots?.add(i)
                   adapter1.AvailableTimeSlotAdapter(context, pmTimeSlots!!,timeStamp!!,this@BookingPresenter)
                   pmRecyclerView.adapter=adapter1
            }
        }
    }

    fun fetchAllAvailableSlot(email:String,start_time:Long,end_time:Long,duration_minutes:Int,interval_minutes:Int) {
        ProgressDialog.getInstance().showProgress(context)
        if (AppStatus.getInstance(context).isConnected()) {
            var availableSlotListObj = JsonObject()
            var jsonArray= JSONArray()
            jsonArray.put(email)
            val jsonObject = JSONObject()
            try {
                jsonObject.put("email", jsonArray)
                jsonObject.put("start_time", start_time)
                jsonObject.put("end_time", end_time)
                jsonObject.put("duration_minutes", duration_minutes)
                jsonObject.put("interval_minutes", interval_minutes)
                val jsonParser = JsonParser()
                availableSlotListObj = jsonParser.parse(jsonObject.toString()) as JsonObject
                //print parameter
                Log.e("availableSlotListReqqq:", " $availableSlotListObj")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            RetrofitCallbacks.getInstace().ApiCallbacksGetAvailableTimings(context, "attorney/avaiable_time", availableSlotListObj, "availableSlotList")
        } else {
            Toast.makeText(context, "No Internet Connection!!!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun successCallBack(body: String?, from: String?) {
        ProgressDialog.getInstance().hideProgress()
        if (body != null) {
            Log.e("success",body)
            val gson = Gson()
            val res: SingleAttorneyResponse = gson.fromJson(body, SingleAttorneyResponse::class.java)
            val allTimeSlots:ArrayList<TimeSlots> = res.attorneysAvailableTimeList?.time_slots!!
            val amTimeSlots:ArrayList<TimeSlots>?= ArrayList()
            val pmTimeSlots:ArrayList<TimeSlots>?=ArrayList()
            adapter.AvailableTimeSlotAdapter(context, amTimeSlots!!,timeStamp!!,this@BookingPresenter)
            amRecyclerView.adapter=adapter
            adapter1.AvailableTimeSlotAdapter(context, pmTimeSlots!!,timeStamp!!,this@BookingPresenter)
            pmRecyclerView.adapter=adapter1
            for(i in allTimeSlots) {
                Log.e("sads", "ass" + i.start)
                val meridium: String =
                    getDateTime(i.start?.toLong()!! * 1000).toUpperCase(Locale.getDefault())
                Log.e("sdfsjfjklasn", meridium)
                if (meridium.equals("AM")) {
                    Log.e("sddsadsa", "asdsadasds");
                    amTimeSlots?.add(i)
                    adapter.AvailableTimeSlotAdapter(
                        context,
                        amTimeSlots!!,
                        timeStamp!!,
                        this@BookingPresenter
                    )
                    amRecyclerView.adapter = adapter
                } else {
                    Log.e("sddsadsa", "asdsadasds1");
                    pmTimeSlots?.add(i)
                    adapter1.AvailableTimeSlotAdapter(
                        context,
                        pmTimeSlots!!,
                        timeStamp!!,
                        this@BookingPresenter
                    )
                    pmRecyclerView.adapter = adapter1
                }
            }
        }
    }

    override fun failureCallBack(failureMsg: String?) {
        ProgressDialog.getInstance().hideProgress()
        if (failureMsg != null) {
            Log.e("failure",failureMsg)
        }
    }

    private fun getDateTime(s: Long):String {
        val sdf1 = SimpleDateFormat("a")
        val netDate1 = Date(s)
        Log.e("sdasdadses","asdsa"+sdf1.format(netDate1))
        return sdf1.format(netDate1)
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun changedItems(meridum:String,startTime:Int,endTime:Int){
        Log.e("fhfhfhhhhhhfh","asd"+startTime+","+endTime)
        var bookingActivity: BookingActivity= context as BookingActivity
        bookingActivity.getTimesSlot(startTime,endTime)
        cnfImg.visibility=View.VISIBLE
        booking.visibility=View.GONE
     /*   booking.backgroundTintList=context.resources.getColorStateList(R.color.toolbar_color)
        booking.setTextColor(context.resources.getColor(R.color.white))
        booking.isEnabled=true
*/
        if(meridum.equals("AM")){
            adapter.selectionEmpty()
            adapter.notifyDataSetChanged()
        }else{
            adapter1.selectionEmpty()
            adapter1.notifyDataSetChanged()
        }
    }
}