package com.rafayee.RHAttorney.MenuModule

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafayee.RH.AttorneyList.Adapter.AttorneyListAdapter
import com.rafayee.RH.MenuModule.Presenter.AlertDialogPresenter
import com.rafayee.RHAttorney.Login.LoginResponseController
import com.rafayee.RHAttorney.MenuModule.Adapter.ProfileAdapter
import com.rafayee.RHAttorney.MenuModule.Adapter.RatingAdapter
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection
import com.skyhope.showmoretextview.ShowMoreTextView

class ProfileActivity : AppCompatActivity() {
    lateinit var alertDialogPresenter: AlertDialogPresenter
    lateinit var ratingAdapter : RatingAdapter
    lateinit var txtView: TextView
    lateinit var txtYoutube : TextView
    lateinit var txtSeeMore : TextView
    lateinit var txtName : TextView
    lateinit var laySeeMore : LinearLayout
    lateinit var imgView : ImageView
    lateinit var circleImage : ImageView
    lateinit var imgBack : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
       // val accadamicList : RecyclerView = findViewById(R.id.recycleview)
        val textView = findViewById<ShowMoreTextView>(R.id.text_view_show_more)
        val edtImg = findViewById<ImageView>(R.id.edt_btn);
        imgView = findViewById(R.id.img_down)
        imgBack = findViewById(R.id.back_btn)
        circleImage = findViewById(R.id.img_profile)
        alertDialogPresenter = AlertDialogPresenter()
        txtView = findViewById(R.id.text_discription)
        txtYoutube = findViewById(R.id.txt_video)
        laySeeMore = findViewById(R.id.ly_see_more)
        txtSeeMore =  findViewById(R.id.txt_see_more)
        txtName = findViewById(R.id.txt_name)
        alertDialogPresenter.AlertDialogPresenter(this)
        ratingAdapter = RatingAdapter(this)

        imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        Glide.with(this)
            .load(ServerApiCollection.IMAGE_URL+ LoginResponseController.myObj?.loginResponseModel!!.clientInfo!!.profilePic)
            .placeholder(R.drawable.profile_ic)
            .into(circleImage)

        txtName.text=LoginResponseController.myObj?.loginResponseModel!!.clientInfo?.firstName+" "+LoginResponseController.myObj?.loginResponseModel!!.clientInfo?.lastName

        txtYoutube.setOnClickListener(View.OnClickListener {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=sxbYzcn28uc"))
            try {
                this@ProfileActivity.startActivity(webIntent)
            } catch (ex: ActivityNotFoundException) {
            }
        })
        laySeeMore.setOnClickListener(View.OnClickListener {
            if (txtSeeMore.text.equals("See More"))
            {
                txtView.setMaxLines(Integer.MAX_VALUE);//your TextView
                txtSeeMore.setText("See Less");
                imgView.setImageResource(R.drawable.up)
            }
            else
            {
                txtView.setMaxLines(4);//your TextView
                txtSeeMore.setText("See More");
                imgView.setImageResource(R.drawable.down_arrow)

            }

        })

/*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/

        supportActionBar?.hide()
        val adapter= ProfileAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
       /* ratingListView.layoutManager = linearLayoutManager
        ratingListView.adapter = ratingAdapter*/
       /* accadamicList.layoutManager = linearLayoutManager
        accadamicList.adapter = adapter
*/


        textView.setShowingLine(6)
      
        edtImg.setOnClickListener(View.OnClickListener {
            alertDialogPresenter.edtProfileAlert()

        })
    }
}

