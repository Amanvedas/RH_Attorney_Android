package com.rafayee.RHAttorney.AppointmentInfoModule.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RHAttorney.HomeFragmentModule.Adapter.ClientInfoAdapter
import com.rafayee.RHAttorney.R
import de.hdodenhof.circleimageview.CircleImageView

class PreviousListAdapter : RecyclerView.Adapter<PreviousListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PreviousListAdapter.ViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_previous_list_layout, null)
        return PreviousListAdapter.ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        /* var nextAvailable: TextView
         var desc: TextView*/
       /* var name: TextView
        var pic: CircleImageView
        var horiLine: View
        var seeMoreLay: LinearLayout

        init {
            name = v.findViewById(R.id.name_)
            *//*  desc = v.findViewById(R.id.desc)
              nextAvailable = v.findViewById(R.id.nextAvailable)*//*
            pic = v.findViewById(R.id.attorneyPic)
            horiLine = v.findViewById(R.id.horiLine)
            seeMoreLay = v.findViewById(R.id.seeMoreLay)
        }*/
    }

}