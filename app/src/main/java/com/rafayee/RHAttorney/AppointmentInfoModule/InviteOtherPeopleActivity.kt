package com.rafayee.RHAttorney.AppointmentInfoModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RHAttorney.R

class InviteOtherPeopleActivity : AppCompatActivity() {
    lateinit var detailsListView : RecyclerView
    lateinit var linearManager : LinearLayoutManager
    lateinit var detailsAdapter: InviteOthersAdapter
    lateinit var listOfDetails : ArrayList<DetailsModel>
    lateinit var detailsModel: DetailsModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_other_people)
        intial()
    }
    fun intial (){
        listOfDetails = ArrayList()
        linearManager = LinearLayoutManager(this)
        detailsAdapter = InviteOthersAdapter()
        detailsModel = DetailsModel("example","example@gmail.com")
        listOfDetails.add(detailsModel)
        detailsModel = DetailsModel("sample","sample@gmail.com")
        listOfDetails.add(detailsModel)
        detailsAdapter.InviteOthersAdapter(listOfDetails,this)
        detailsListView = findViewById(R.id.details_list_view)
        detailsListView.layoutManager = linearManager
        detailsListView.adapter=detailsAdapter

    }
    fun recycleData(name : String,email:String){
        detailsModel = DetailsModel(name,email)
    }
}