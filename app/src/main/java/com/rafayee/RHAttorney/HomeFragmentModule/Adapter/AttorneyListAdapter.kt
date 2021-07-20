package com.rafayee.RHAttorney.AttorneyList.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RHAttorney.AppointmentInfoModule.AppointmentInfoActivity

import com.rafayee.RHAttorney.HomeFragmentModule.Model.AttorneyList
import com.rafayee.RHAttorney.R
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

public class AttorneyListAdapter : RecyclerView.Adapter<AttorneyListAdapter.ViewHolder>() {
    lateinit var activity: Context
    lateinit var data: MutableList<AttorneyList>
    var timeText: String? = ""
    var from: String? = ""
    var count: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.available_attorney_list, null)
        return ViewHolder(layout)
    }

    fun AttorneyListAdapter(
        context: Context,
        from: String/*, objects: ArrayList<AboutusModel>*/
    ) {
        this.activity = context
        this.from = from
        if (!from.equals("total")) {
            count=3
        } else {
            count = 2
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("sdfsdfsvdscd", "Asdas" + count)

        holder.itemView.setOnClickListener(View.OnClickListener {
            activity.startActivity(Intent(activity, AppointmentInfoActivity::class.java))

        })
        if (!from.equals("total")) {
           if (position==0){
               holder.txtAttend.isVisible=true
           }else{
               holder.txtAttend.isVisible=false
           }
        }else{
            holder.txtAttend.isVisible=false

        }
        Log.e("position",":: "+position)
        /*if (count == 4) {
            Log.e("sdfsdfsvdscd", "asdsd" + count)
            holder.seeMoreLay.visibility = View.GONE
            if (position == 3) {
                if (!from.equals("total")) {
                    holder.horiLine.visibility = View.GONE
                } else {
                    holder.horiLine.visibility = View.VISIBLE
                }
            } else {
                holder.horiLine.visibility = View.VISIBLE
            }
        } else {
            Log.e("sdfsdfsvdscd", "fsdss" + count)
            if (position == 2) {
                Log.e("sdfsdfsvdscd", "Asdas" + count)
                holder.seeMoreLay.visibility = View.VISIBLE
                if (!from.equals("total")) {
                    holder.horiLine.visibility = View.GONE
                } else {
                    holder.horiLine.visibility = View.VISIBLE
                }
            } else {
                Log.e("sdfsdfsvdscd", "ssdfds" + count)
                holder.seeMoreLay.visibility = View.GONE
                holder.horiLine.visibility = View.VISIBLE
            }
        }
        if (from.equals("all")) {
            holder.nextAvailable.visibility = View.GONE
        }
        if (from.equals("total")) {
            holder.seeMoreLay.visibility = View.GONE
        }
        holder.name.text = data[position].firstName + " " + data[position].lastName
*/
        holder.seeMoreLay.visibility = View.GONE
        if (from.equals("non")) {
            holder.desc.visibility = View.VISIBLE
        } else if(from.equals("total")){
            holder.desc.visibility = View.GONE
        }else{
            holder.nextAvailable.visibility=View.GONE
            holder.desc.visibility = View.GONE
        }

        /*Glide.with(activity)
            .load(IMAGE_URL + data[position].profilePic)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.pic)*/
        /*if(position%2==0) {
            Glide.with(activity)
                .load(R.drawable.fone)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.pic)
        }else{
            Glide.with(activity)
                .load(R.drawable.sone)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.pic)
        }*/
       /* getDateTime(data[position].time_slots?.get(0)?.start!!.toLong() * 1000)
        holder.nextAvailable.text = "Next Available: $timeText"
        //holder.nextAvailable.text = ""
        if (position == data.size - 1) {
            holder.horiLine.visibility = View.GONE
        }*/
/*
        holder.itemView.setOnClickListener {
            var pos = holder.adapterPosition % 2
            activity.startActivity(
                Intent(activity, AttorneyProfileActivity::class.java)
                    .putExtra("pos", pos)
                    .putExtra("email", data[holder.adapterPosition].attorneyMail)
                    .putExtra(
                        "name",
                        data[holder.adapterPosition].firstName + " " + data[holder.adapterPosition].lastName
                    )
                    .putExtra("picImage", data[holder.adapterPosition].profilePic)
            )
        }
*/
/*
        holder.seeMoreLay.setOnClickListener {
            activity.startActivity(Intent(activity, AllAttorneyListActivity::class.java))
            // count=4
            //notifyDataSetChanged()
        }
*/
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        // return data.size
        return count
    }

    private fun getDateTime(s: Long) {
        val sdf1 = SimpleDateFormat("MMM dd")
        val netDate1 = Date(s)
        timeText = sdf1.format(netDate1)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var nextAvailable: TextView
        var desc: TextView
        var name: TextView
        var txtAttend : TextView
        var pic: CircleImageView
        var horiLine: View
        var seeMoreLay: LinearLayout

        init {
            name = v.findViewById(R.id.name)
            desc = v.findViewById(R.id.desc)
            nextAvailable = v.findViewById(R.id.nextAvailable)
            pic = v.findViewById(R.id.attorneyPic)
            horiLine = v.findViewById(R.id.horiLine)
            seeMoreLay = v.findViewById(R.id.seeMoreLay)
            txtAttend = v.findViewById(R.id.txt_attend)
        }
    }
}