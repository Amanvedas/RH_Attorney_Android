package com.rafayee.RHAttorney.Bookings.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RHAttorney.HomeFragmentModule.Model.TimeSlots
import com.rafayee.RHAttorney.Presenter.BookingPresenter
import com.rafayee.RHAttorney.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

public class FinalAvailableTimeSlotAdapter : RecyclerView.Adapter<FinalAvailableTimeSlotAdapter.ViewHolder>() {
    lateinit var activity: Context
    lateinit var data:ArrayList<TimeSlots>
    var dateText:String?=""
    var timeText:String?=""
    var timeStamp:Long?=0
    lateinit var presenter: BookingPresenter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.available_time_slot_list, null)
        return ViewHolder(
            layout
        )
    }

    fun AvailableTimeSlotAdapter(context: Context,data:ArrayList<TimeSlots>,timeStamp:Long,presenter:BookingPresenter/*,secondaryList:ArrayList<String*/) {
        this.activity = context
        this.data = data
        this.timeStamp=timeStamp
        this.presenter=presenter
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("sadsadss","asdasdas")
        getDateTime(data[position].start?.toLong()!!*1000)
        if(timeStamp==data[position].start?.toLong()){
            holder.time.backgroundTintList= activity.resources.getColorStateList(R.color.toolbar_color)
            holder.time.setTextColor(activity.resources.getColor(R.color.white))
        }else{
            holder.time.backgroundTintList=activity.resources.getColorStateList(R.color.white)
            holder.time.setTextColor(activity.resources.getColor(R.color.black))
        }
        holder.time.setOnClickListener {
            timeStamp=data[holder.adapterPosition].start?.toLong()
            var position:Int=holder.adapterPosition
            Log.e("sas","sad"+position)
            notifyDataSetChanged()
            if(timeText?.toUpperCase()?.contains("AM")!!) {
                presenter.changedItems("PM",data[position].start!!,data[position].end!!)
            }else{
                presenter.changedItems("AM",data[position].start!!,data[position].end!!)
            }
        }
        holder.time.text=timeText
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return data.size
    }
    private fun getDateTime(s: Long) {
        val sdf = SimpleDateFormat("EEE,MM/dd")
        val netDate = Date(s)
        dateText=sdf.format(netDate)
        val sdf1 = SimpleDateFormat("hh:mm a")
        val netDate1 = Date(s)
        timeText=sdf1.format(netDate1).replace("am", "AM").replace("pm", "PM")
        Log.e("asasda","asas  $dateText $timeText")
    }
    fun selectionEmpty(){
        timeStamp=0;
    }
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var time: TextView
        init {
            time = v.findViewById(R.id.time)
        }
    }
}