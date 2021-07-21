package com.rafayee.RHAttorney.MenuModule

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.Login.Model.LoginResponseModel
import com.rafayee.RHAttorney.MenuModule.Adapter.ProfileAdapter
import com.rafayee.RHAttorney.MenuModule.Adapter.RatingAdapter
import com.rafayee.RHAttorney.MenuModule.Presenter.AlertDialogPresenter
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection
import com.skyhope.showmoretextview.ShowMoreTextView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ProfileActivity : AppCompatActivity(), RetrofitCallbacks.ServerResponseInterface {
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
    lateinit var  loginResponseModel: LoginResponseModel
   // lateinit var txt_name : TextView
    lateinit var txt_job : TextView
    lateinit var txt_disc : TextView
    lateinit var text_discription : TextView
    lateinit var txt_award_edc_disc1 : TextView
    lateinit var txt_award_edc_disc2 : TextView
    lateinit var txt_edc_disc : TextView
    lateinit var txt_edc_disc1 : TextView
    lateinit var education_recycler:RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
       // val accadamicList : RecyclerView = findViewById(R.id.recycleview)
        val textView = findViewById<ShowMoreTextView>(R.id.text_view_show_more)
        val edtImg = findViewById<ImageView>(R.id.edt_btn);
        imgView = findViewById(R.id.img_down)
        imgBack = findViewById(R.id.back_btn)
        circleImage = findViewById(R.id.img_profile)
        loginResponseModel = Gson().fromJson(getSharedPreferences("LoginPref", 0).getString("userInfo","").toString(), LoginResponseModel::class.java)
        alertDialogPresenter = AlertDialogPresenter()
        txtView = findViewById(R.id.text_discription)
        txtYoutube = findViewById(R.id.txt_video)
        laySeeMore = findViewById(R.id.ly_see_more)
        txtSeeMore =  findViewById(R.id.txt_see_more)
        txtName = findViewById(R.id.txt_name)
        txt_job = findViewById(R.id.txt_job)
        txt_disc = findViewById(R.id.txt_disc)
        text_discription = findViewById(R.id.text_discription)
        txt_award_edc_disc1 = findViewById(R.id.txt_award_edc_disc1)
        txt_award_edc_disc2 = findViewById(R.id.txt_award_edc_disc2)
        txt_edc_disc = findViewById(R.id.txt_edc_disc)
        txt_edc_disc1 = findViewById(R.id.txt_edc_disc1)
        education_recycler=findViewById(R.id.education_recycler)
        alertDialogPresenter.AlertDialogPresenter(this)
        ratingAdapter = RatingAdapter(this)
        RetrofitCallbacks.getInstace().initializeServerInterface(this)
        imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        val pref = getSharedPreferences("LoginPref", 0)
        profileInfoApi(this,pref.getString("emailID","").toString())


      //  txtName.text=loginResponseModel.attorneysList.get(0).firstName+" "+loginResponseModel.attorneysList.get(0).lastName

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

        val linearLayoutManager = LinearLayoutManager(this)
        education_recycler.layoutManager = linearLayoutManager

       /* accadamicList.layoutManager = linearLayoutManager
        accadamicList.adapter = adapter
*/


        textView.setShowingLine(6)
      
        edtImg.setOnClickListener(View.OnClickListener {
            alertDialogPresenter.edtProfileAlert()

        })
    }
    fun profileInfoApi(context: Context, email:String){
        ProgressDialog.getInstance().showProgress(context)
        var emialObject: JsonObject = JsonObject()
        val jsonObject = JSONObject()

        try {
            jsonObject.put("emailID", email)
            val jsonParser = JsonParser()
            emialObject = jsonParser.parse(jsonObject.toString()) as JsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        RetrofitCallbacks.getInstace().apiCallBacks(context,"attorney/FetchAttorney",emialObject,"AppointmentInfo")
    }
    var arrayList: ArrayList<String> = ArrayList()
    override fun successCallBack(body: String?, from: String?) {
        ProgressDialog.getInstance().hideProgress()
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(body)
            if (jsonObject.getString("response").equals("3")) {
                var jsonarray: JSONArray
                var jsonobj:JSONObject
                var jsonuserinfo:JSONObject
                jsonarray=   jsonObject.getJSONArray("attorneysList")
                jsonobj = jsonarray.getJSONObject(0)
                txtName.text=jsonobj.getString("firstName")+" "+jsonobj.getString("lastName")

                jsonuserinfo= jsonobj.getJSONObject("userInfo")
                txt_job.text=jsonuserinfo.getString("designation")
                txt_disc.text=jsonobj.getJSONObject("address").getString("street")+" "+jsonobj.getJSONObject("address").getString("state")+
                        jsonobj.getJSONObject("address").getString("postalCode")

                txt_award_edc_disc1.text=jsonuserinfo.getJSONObject("biography").getJSONArray("awards").getString(0)
                txt_award_edc_disc2.text=jsonuserinfo.getJSONObject("biography").getJSONArray("awards").getString(0)

                arrayList.add(jsonuserinfo.getJSONObject("biography").getJSONArray("educationBarAdmitions").getString(0))
                arrayList.add(jsonuserinfo.getJSONObject("biography").getJSONArray("educationBarAdmitions").getString(1))
                arrayList.add(jsonuserinfo.getJSONObject("biography").getJSONArray("educationBarAdmitions").getString(2))

                val adapter= ProfileAdapter(this,arrayList)
                education_recycler.adapter = adapter

                Glide.with(this)
                    .load(ServerApiCollection.IMAGE_URL+jsonobj.getString("profilePic"))
                    .placeholder(R.drawable.profile_ic)
                    .into(circleImage)

            }
        }catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    override fun failureCallBack(failureMsg: String?) {
        ProgressDialog.getInstance().hideProgress()
        Log.e("Working","no:: ")
    }
}

