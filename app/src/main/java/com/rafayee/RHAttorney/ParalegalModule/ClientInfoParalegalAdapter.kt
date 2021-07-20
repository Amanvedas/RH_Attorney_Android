package com.rafayee.RHAttorney.ParalegalModule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RHAttorney.AppointmentInfoModule.Adapter.PreviousListAdapter
import com.rafayee.RHAttorney.AppointmentInfoModule.DetailsModel
import com.rafayee.RHAttorney.R

class ClientInfoParalegalAdapter : RecyclerView.Adapter<ClientInfoParalegalAdapter.ViewHolder>() {

    lateinit var data: ArrayList<DetailsModel>
    lateinit var activity: Context

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var name: TextView = v.findViewById(R.id.txt_name)

    }
    fun ClientInfoParalegalAdapter(data: ArrayList<DetailsModel>, activity: Context) {
        this.data = data
        this.activity = activity
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClientInfoParalegalAdapter.ViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.client_info_paralegal_item, null)
        return ClientInfoParalegalAdapter.ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ClientInfoParalegalAdapter.ViewHolder, position: Int) {
        holder.name.text=data.get(position).name.toString()

    }
}