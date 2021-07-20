package com.rafayee.RHAttorney.AppointmentInfoModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.VideoCall.View.CallConnectActivity

class InviteOtherPeopleActivity : AppCompatActivity() {
    lateinit var detailsListView : RecyclerView
    lateinit var linearManager : LinearLayoutManager
    lateinit var detailsAdapter: InviteOthersAdapter
    lateinit var listOfDetails : ArrayList<DetailsModel>
    lateinit var detailsModel: DetailsModel
    lateinit var backImag : ImageView
    lateinit var imgAdd : ImageView
    lateinit var edtName :EditText
    lateinit var edtEmail: EditText
    lateinit var btnShare : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_other_people)
        supportActionBar?.hide()
        intial()
    }
    fun intial (){
        listOfDetails = ArrayList()
        backImag = findViewById(R.id.back_btn)
        btnShare = findViewById(R.id.btn_share)
        edtName = findViewById(R.id.edit_name)
        edtEmail = findViewById(R.id.edit_email)
        linearManager = LinearLayoutManager(this)
        detailsAdapter = InviteOthersAdapter()
        imgAdd = findViewById(R.id.img_add)
        detailsModel = DetailsModel()
        detailsModel.name="example"
        detailsModel.email="example@gmail.com"
        listOfDetails.add(detailsModel)
        detailsModel = DetailsModel()
        detailsModel.name="sample"
        detailsModel.email="sample@gmail.com"
        listOfDetails.add(detailsModel)
        detailsAdapter.InviteOthersAdapter(listOfDetails,this)
        detailsListView = findViewById(R.id.details_list_view)
        detailsListView.layoutManager = linearManager
        detailsListView.adapter=detailsAdapter
        backImag.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        imgAdd.setOnClickListener(View.OnClickListener {
            recycleData(edtName.text.toString(),edtEmail.text.toString())

        })
        btnShare.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, CallConnectActivity::class.java))

        })

    }
    fun recycleData(name : String,email:String){
        detailsModel = DetailsModel()
        detailsModel.name=name
        detailsModel.email=email
        listOfDetails.add(detailsModel)
        detailsAdapter.InviteOthersAdapter(listOfDetails,this)
        detailsListView.adapter=detailsAdapter
        detailsAdapter.notifyDataSetChanged()

    }

/*
    override fun onBackPressed() {
        startActivity(Intent(this, AppointmentInfoActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
        super.onBackPressed()

    }
*/

}