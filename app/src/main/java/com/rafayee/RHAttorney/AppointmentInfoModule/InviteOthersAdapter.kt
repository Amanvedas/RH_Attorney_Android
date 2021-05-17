package com.rafayee.RHAttorney.AppointmentInfoModule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RH.AttorneyList.Adapter.AttorneyListAdapter
import com.rafayee.RHAttorney.HomeFragmentModule.Model.AttorneyList
import com.rafayee.RHAttorney.R

class InviteOthersAdapter : RecyclerView.Adapter<InviteOthersAdapter.ViewHolder>() {
    lateinit var data: ArrayList<DetailsModel>
    lateinit var activity: Context

    fun InviteOthersAdapter(data: ArrayList<DetailsModel>, activity: Context) {
        this.data = data
        this.activity = activity
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
         var email: TextView = v.findViewById(R.id.txt_email)
         var name: TextView = v.findViewById(R.id.txt_name)
        var imgCancel :ImageView = v.findViewById(R.id.img_cancel)

    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InviteOthersAdapter.ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.details_item_layout, null)
        return InviteOthersAdapter.ViewHolder(layout)
    }

    override fun getItemCount(): Int {
    return data.size
    }

    override fun onBindViewHolder(holder: InviteOthersAdapter.ViewHolder, position: Int) {

        holder.email.text=data.get(position).email
        holder.name.text=data.get(position).name
        holder.imgCancel.setOnClickListener(View.OnClickListener {
            data.removeAt(position)
            notifyDataSetChanged()
        })
    }
}