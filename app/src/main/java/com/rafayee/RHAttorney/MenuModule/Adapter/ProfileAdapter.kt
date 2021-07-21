package com.rafayee.RHAttorney.MenuModule.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RHAttorney.R

class ProfileAdapter ( var context: Context,var arrayList: ArrayList<String>) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    class ViewHolder (v: View): RecyclerView.ViewHolder(v)  {
         var txt_edu: TextView?=null
        init {
            txt_edu = v.findViewById(R.id.txt_edu)

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.profile_recycle_item, null)
        return ProfileAdapter.ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ProfileAdapter.ViewHolder, position: Int) {
        holder.txt_edu?.setText(arrayList.get(position))
    }
}