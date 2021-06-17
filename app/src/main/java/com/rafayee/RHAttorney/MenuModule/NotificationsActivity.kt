package com.rafayee.RH.MenuModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mikhaellopez.circularimageview.CircularImageView
import com.rafayee.RH.AttornyProfile.Adapter.NotificationAdapter
import com.rafayee.RHAttorney.Login.LoginResponseController
import com.rafayee.RHAttorney.MenuModule.ProfileActivity
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection


class NotificationsActivity : AppCompatActivity() {
    lateinit var notificationListView: RecyclerView
    lateinit var notificationList: ArrayList<NotificationModel>
    lateinit var img_cancel: ImageView
    lateinit var imgPic: CircularImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        supportActionBar?.hide()
        img_cancel = findViewById(R.id.img_cancel)
        imgPic = findViewById(R.id.profile_pic)
        notificationListView = findViewById(R.id.notification_list_view)

        Glide.with(this)
            .load(ServerApiCollection.IMAGE_URL+ LoginResponseController.myObj?.loginResponseModel!!.clientInfo!!.profilePic)
            .placeholder(R.drawable.profile_ic)
            .into(imgPic)

        imgPic.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))

        })


        notificationListView.setLayoutManager(LinearLayoutManager(this))
        notificationListView.setHasFixedSize(true)
        notificationList = ArrayList()
        val string = "Hi Manish! You have an upcoming consultation in 2 hours, click here to complete the additional questions to make your consultation more productive."
        notificationList.add(NotificationModel(string, System.currentTimeMillis().toString(), R.drawable.clock_1))
        notificationList.add(NotificationModel(string, System.currentTimeMillis().toString(), R.drawable.clock_1))
        val notificationAdapter = NotificationAdapter()
        notificationAdapter.NotificationAdapter(this@NotificationsActivity, notificationList)
        notificationListView.setAdapter(notificationAdapter)
        img_cancel.setOnClickListener { onBackPressed() }
    }
}