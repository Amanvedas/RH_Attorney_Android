package com.rafayee.RHAttorney.MenuModule.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RH.AttorneyList.Adapter.AttorneyListAdapter
import com.rafayee.RHAttorney.R

class ProfileAdapter (private var context: Context) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {
    lateinit var activity: Context
     var arrayList: ArrayList<String> = ArrayList()

    class ViewHolder (v: View): RecyclerView.ViewHolder(v)  {

    }

    fun ProfileAdapter(context: Context, arrayList: ArrayList<String>) {
        this.activity = context
        this.arrayList = arrayList

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.profile_recycle_item, null)
        return ProfileAdapter.ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ProfileAdapter.ViewHolder, position: Int) {
    }
}