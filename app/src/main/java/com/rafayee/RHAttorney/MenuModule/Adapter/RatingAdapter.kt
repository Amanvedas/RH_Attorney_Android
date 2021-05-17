package com.rafayee.RHAttorney.MenuModule.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RHAttorney.R

class RatingAdapter(private var context: Context) : RecyclerView.Adapter<RatingAdapter.ViewHolder>() {
    class ViewHolder (v: View): RecyclerView.ViewHolder(v)  {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingAdapter.ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.profile_recycle_item, parent)
       return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: RatingAdapter.ViewHolder, position: Int) {

    }
}