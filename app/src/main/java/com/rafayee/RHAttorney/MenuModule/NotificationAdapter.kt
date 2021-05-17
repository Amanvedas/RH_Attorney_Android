package com.rafayee.RH.AttornyProfile.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RH.Helpers.TimeAgo
import com.rafayee.RH.MenuModule.NotificationModel
import com.rafayee.RHAttorney.R


class NotificationAdapter: RecyclerView.Adapter<NotificationAdapter.MyPromoHolder>() {
    lateinit var context: Context
    private var notificationList: ArrayList<NotificationModel> = ArrayList()
    fun NotificationAdapter(
        context: Context,
        notificationList: ArrayList<NotificationModel>
    )  {
        this.context = context
        this.notificationList = notificationList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPromoHolder {
        val layout: View = LayoutInflater.from(parent.context).inflate(
            R.layout.notification_item,
            null
        )
        return MyPromoHolder(layout)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MyPromoHolder, position: Int) {
        val timeAgo = TimeAgo()
        val MyFinalValue: String? = timeAgo.covertTimeToText(notificationList.get(position).time)
        holder.textViewTiming.setText(MyFinalValue)

        holder.itemView.setOnTouchListener(OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    holder.rl_notify.background =
                        context.resources.getDrawable(R.drawable.blue_boarder_fill)
                    holder.textViewDisc.setTextColor(context.resources.getColor(R.color.white))
                    holder.textViewTiming.setTextColor(context.resources.getColor(R.color.white))
                    holder.img_clock.setImageDrawable(context.resources.getDrawable(R.drawable.clock_2))
                    holder.imgIcon.setImageDrawable(context.resources.getDrawable(R.drawable.logo_3))
                }
                MotionEvent.ACTION_UP -> {
                    holder.rl_notify.background =
                        context.resources.getDrawable(R.drawable.blue_boarder)
                    holder.textViewDisc.setTextColor(context.resources.getColor(R.color.black))
                    holder.textViewTiming.setTextColor(context.resources.getColor(R.color.black))
                    holder.img_clock.setImageDrawable(context.resources.getDrawable(R.drawable.clock_1))
                    holder.imgIcon.setImageDrawable(context.resources.getDrawable(R.drawable.logo_2))
                }
                MotionEvent.ACTION_CANCEL -> {
                    holder.rl_notify.background =
                        context.resources.getDrawable(R.drawable.blue_boarder_fill)
                    holder.textViewDisc.setTextColor(context.resources.getColor(R.color.white))
                    holder.textViewTiming.setTextColor(context.resources.getColor(R.color.white))
                    holder.img_clock.setImageDrawable(context.resources.getDrawable(R.drawable.clock_2))
                    holder.imgIcon.setImageDrawable(context.resources.getDrawable(R.drawable.logo_3))
                }
            }
            false
        })
        holder.itemView.setOnClickListener(View.OnClickListener {
            Log.e("notify","adapter")
                //context.startActivity(Intent(context, FiltersActivity::class.java))
        })
    }
    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    class MyPromoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewDisc : TextView
        var textViewTiming : TextView
        var imgIcon : ImageView
        var img_clock : ImageView
        var rl_notify : RelativeLayout
        init {
            textViewDisc = itemView.findViewById(R.id.txt_disc)
            textViewTiming  = itemView.findViewById(R.id.txt_time)
            imgIcon  = itemView.findViewById(R.id.img_logo)
            img_clock  = itemView.findViewById(R.id.img_clock)
            rl_notify  = itemView.findViewById(R.id.rl_notify)
        }
    }
}