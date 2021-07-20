package com.rafayee.RHAttorney.Bookings.View

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter
import java.text.SimpleDateFormat
import java.util.*
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RHAttorney.Presenter.BookingPresenter
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks

class BookingActivity : AppCompatActivity() {
    //lateinit var calendarView: CalendarView
/*
    lateinit var amRecyclerView:RecyclerView
    lateinit var pmRecyclerView:RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var linearLayoutManager1: LinearLayoutManager
    var presenter: BookingPresenter?=null
    lateinit var alertDialogPresenter: AlertDialogPresenter
    lateinit var cnfBook:TextView
    lateinit var back:ImageView
    var timeStamp:Int=0
    var timeStamp1:Int=0
    lateinit var startsFrom:TextView
    lateinit var cnfImg:ImageView
    var startsFromText:String?=""
    lateinit var proCal:MaterialCalendarView
    lateinit var cardCnf:CardView

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        supportActionBar?.hide()
        alertDialogPresenter = AlertDialogPresenter()
        alertDialogPresenter.AlertDialogPresenter(this)

        ProgressDialog.getInstance().hideProgress()
        timeStamp=intent.getIntExtra("startTime",0)
        timeStamp1=intent.getIntExtra("endTime",0)
        var email:String?=intent.getStringExtra("email")
        var name:String?=intent.getStringExtra("name")
        amRecyclerView=findViewById(R.id.amRecyclerView)
        pmRecyclerView=findViewById(R.id.pmRecyclerView)
        cnfBook=findViewById(R.id.cnfBook)
        cnfImg=findViewById(R.id.cnfImg)
        cardCnf=findViewById(R.id.cardCnf)
        back=findViewById(R.id.img_back)
        startsFrom=findViewById(R.id.startsFrom)
        proCal=findViewById(R.id.proCal)


        Log.e("hgfcgh","gh:"+timeStamp+", "+timeStamp1)



        startsFrom.text="Available Starting times for "+intent.getStringExtra("startsFrom")

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager1 = LinearLayoutManager(this)
        amRecyclerView.layoutManager=linearLayoutManager
        pmRecyclerView.layoutManager=linearLayoutManager1
        presenter= BookingPresenter()
       // RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
        var cal:Calendar= Calendar.getInstance()
        cal.timeInMillis=timeStamp.toLong()*1000
        if(intent.getStringExtra("from")!=null){
            if(timeStamp.toLong()*1000<System.currentTimeMillis()){
               // cardCnf.visibility=View.GONE
                cnfImg.visibility=View.VISIBLE
            }else{
               // cardCnf.visibility=View.VISIBLE
                cnfImg.visibility=View.VISIBLE
            }
            proCal.state().edit().setMinimumDate(cal).commit()
            proCal.setSelectedDate(cal)
            presenter!!.getInstance(this@BookingActivity,amRecyclerView,pmRecyclerView,0,cardCnf,cnfImg)
        }else{
            cardCnf.visibility=View.GONE
            cnfImg.visibility=View.VISIBLE
            proCal.state().edit().setMinimumDate(cal).commit()
            proCal.setSelectedDate(cal)
            presenter!!.getInstance(this@BookingActivity,amRecyclerView,pmRecyclerView,timeStamp.toLong(),cardCnf,cnfImg)
        }
        var calendar:Calendar= proCal.selectedDate.calendar
        var startTime:Long
        var year: Int = calendar.get(Calendar.YEAR)
        var month: Int = calendar.get(Calendar.MONTH)
        var day: Int = calendar.get(Calendar.DATE)

        startTime = System.currentTimeMillis()/1000
        cal.set(year, month, day, 23, 0, 0)
        var endTime = (cal.timeInMillis) / 1000
        proCal.isDynamicHeightEnabled=true
        proCal.setOnMonthChangedListener { widget, date ->
            Log.e("ssads","dsss")
            widget.isDynamicHeightEnabled=true
        }
        val sdf2 = SimpleDateFormat("MMM yyyy")
        proCal.setTitleFormatter(DateFormatTitleFormatter(sdf2))
//        presenter?.fetchAllAvailableSlot(email!!, startTime, endTime, 30, 30)
        proCal.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
          //  cardCnf.visibility=View.VISIBLE
            cnfImg.visibility=View.VISIBLE
            Log.e("sadasdasfs","assas"+date.calendar.time)
            startsFrom(date.calendar.timeInMillis)
            var calendar: Calendar = Calendar.getInstance()
            var year: Int = calendar.get(Calendar.YEAR)
            var month: Int = calendar.get(Calendar.MONTH)
            var day: Int = calendar.get(Calendar.DATE)

            var year1: Int = date.calendar.get(Calendar.YEAR)
            var month1: Int = date.calendar.get(Calendar.MONTH)
            var day1: Int = date.calendar.get(Calendar.DATE)
            val startTime:Long
            if (year == year1 && month == month1 && day == day1) {
                Log.e("calll","assIF")
                startTime = (System.currentTimeMillis())/1000
                date.calendar.set(year1, month1, day1, 23, 0, 0)
                var endTime = (date.calendar.timeInMillis) / 1000
                Log.e("asdsdsds", "asdas" + date.calendar.time)
                //  fetchAllAvailableSlot()
//                presenter?.fetchAllAvailableSlot(email!!, startTime, endTime, 30, 30)
            } else {
                Log.e("calll","assELSE")
                date.calendar.set(year1, month1, day1, 9, 0, 0)
                startTime = (date.calendar.timeInMillis) / 1000
                date.calendar.set(year1, month1, day1, 23, 0, 0)
                var endTime = (date.calendar.timeInMillis) / 1000
//                presenter?.fetchAllAvailableSlot(email!!, startTime, endTime, 30, 30)
            }
        })
        cnfImg.setOnClickListener {

            alertDialogPresenter.calAlertDialog(this)

*/
/*
            if(intent.hasExtra("login")){

            }else{

                //navigating(name!!,email!!)


            }
*//*


        }
        back.setOnClickListener {
            onBackPressed()
        }
    }

    fun getTimesSlot(startTime:Int,endTime:Int) {
        this.timeStamp=startTime
        this.timeStamp1=endTime
    }

    fun navigating(name:String,email:String){
*/
/*
        startActivity(Intent(this@BookingActivity, FirstAndLastNameEntry::class.java)
                    .putExtra("startTime", timeStamp)
                    .putExtra("endTime", timeStamp1)
                    .putExtra("email", email)
                    .putExtra("name", name))
*//*

    }

    fun startsFrom(s:Long){
        val sdf2 = SimpleDateFormat("dd MMM, yyyy")
        val netDate2 = Date(s)
        startsFromText = sdf2.format(netDate2)
        startsFrom.text="Available Starting times for "+startsFromText
    }
    override fun onRestart() {
        ProgressDialog.getInstance().hideProgress()
        timeStamp=intent.getIntExtra("startTime",0)
        timeStamp1=intent.getIntExtra("endTime",0)
        var email:String?=intent.getStringExtra("email")
        var name:String?=intent.getStringExtra("name")

        amRecyclerView=findViewById(R.id.amRecyclerView)
        pmRecyclerView=findViewById(R.id.pmRecyclerView)
        cnfBook=findViewById(R.id.cnfBook)
        cnfImg=findViewById(R.id.cnfImg)
        cardCnf=findViewById(R.id.cardCnf)
        back=findViewById(R.id.img_back)
        startsFrom=findViewById(R.id.startsFrom)
        proCal=findViewById(R.id.proCal)

        startsFrom.text="Available Starting times for "+intent.getStringExtra("startsFrom")

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager1 = LinearLayoutManager(this)
        amRecyclerView.layoutManager=linearLayoutManager
        pmRecyclerView.layoutManager=linearLayoutManager1
        presenter= BookingPresenter()
      //  RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
        var cal:Calendar= Calendar.getInstance()
        cal.timeInMillis=timeStamp.toLong()*1000
        if(intent.getStringExtra("from")!=null){
          //  cardCnf.visibility=View.VISIBLE
            cnfImg.visibility=View.VISIBLE
            proCal.state().edit().setMinimumDate(cal).commit()
            proCal.setSelectedDate(cal)
            presenter!!.getInstance(this@BookingActivity,amRecyclerView,pmRecyclerView,0,cardCnf,cnfImg)

        }else{
          //  cardCnf.visibility=View.GONE
            cnfImg.visibility=View.VISIBLE
            proCal.state().edit().setMinimumDate(cal).commit()
            proCal.setSelectedDate(cal)
            presenter!!.getInstance(this@BookingActivity,amRecyclerView,pmRecyclerView,timeStamp.toLong(),cardCnf,cnfImg)
        }

        var calendar:Calendar= proCal.selectedDate.calendar
        var startTime:Long
        var year: Int = calendar.get(Calendar.YEAR)
        var month: Int = calendar.get(Calendar.MONTH)
        var day: Int = calendar.get(Calendar.DATE)
        startTime = System.currentTimeMillis()/1000
        cal.set(year, month, day, 23, 0, 0)
        var endTime = (cal.timeInMillis) / 1000
        proCal.isDynamicHeightEnabled=true
        proCal.setOnMonthChangedListener { widget, date ->
            Log.e("ssads","dsss")
            widget.isDynamicHeightEnabled=true
        }
        val sdf2 = SimpleDateFormat("MMM yyyy")
        proCal.setTitleFormatter(DateFormatTitleFormatter(sdf2))
        presenter?.fetchAllAvailableSlot(email!!, startTime, endTime, 30, 30)
        proCal.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
           // cardCnf.visibility=View.VISIBLE
            cnfImg.visibility=View.VISIBLE
            Log.e("sadasdasfs","assas"+date.calendar.time)
            startsFrom(date.calendar.timeInMillis)
            var calendar: Calendar = Calendar.getInstance()
            var year: Int = calendar.get(Calendar.YEAR)
            var month: Int = calendar.get(Calendar.MONTH)
            var day: Int = calendar.get(Calendar.DATE)
            var year1: Int = date.calendar.get(Calendar.YEAR)
            var month1: Int = date.calendar.get(Calendar.MONTH)
            var day1: Int = date.calendar.get(Calendar.DATE)
            val startTime:Long
            if (year == year1 && month == month1 && day == day1) {
                Log.e("calll","assIF")
                startTime = (System.currentTimeMillis())/1000
                date.calendar.set(year1, month1, day1, 23, 0, 0)
                var endTime = (date.calendar.timeInMillis) / 1000
                Log.e("asdsdsds", "asdas" + date.calendar.time)
                //  fetchAllAvailableSlot()
                presenter?.fetchAllAvailableSlot(email!!, startTime, endTime, 30, 30)
            } else {
                Log.e("calll","assELSE")
                date.calendar.set(year1, month1, day1, 9, 0, 0)
                startTime = (date.calendar.timeInMillis) / 1000
                date.calendar.set(year1, month1, day1, 23, 0, 0)
                var endTime = (date.calendar.timeInMillis) / 1000
                presenter?.fetchAllAvailableSlot(email!!, startTime, endTime, 30, 30)
            }
        })
        super.onRestart()
    }
}*/

    lateinit var amRecyclerView:RecyclerView
    lateinit var pmRecyclerView:RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var linearLayoutManager1: LinearLayoutManager
    var presenter: BookingPresenter?=null
    lateinit var cnfBook:TextView
    lateinit var back:ImageView
    var timeStamp:Int=0
    var timeStamp1:Int=0
    lateinit var name:String
    lateinit var email:String
    lateinit var startsFrom:TextView
    lateinit var cnfImg:ImageView
    var startsFromText:String?=""
    lateinit var proCal:MaterialCalendarView
    lateinit var cardCnf:CardView

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        supportActionBar?.hide()
        ProgressDialog.getInstance().hideProgress()
        timeStamp=intent.getIntExtra("startTime",0)
        timeStamp1=intent.getIntExtra("endTime",0)
        email = intent.getStringExtra("email").toString()
        name= intent.getStringExtra("name").toString()
        amRecyclerView=findViewById(R.id.amRecyclerView)
        pmRecyclerView=findViewById(R.id.pmRecyclerView)
        cnfBook=findViewById(R.id.cnfBook)
        cnfImg=findViewById(R.id.cnfImg)
        cardCnf=findViewById(R.id.cardCnf)
        back=findViewById(R.id.img_back)
        startsFrom=findViewById(R.id.startsFrom)
        proCal=findViewById(R.id.proCal)


        Log.e("hgfcgh","gh:"+timeStamp+", "+timeStamp1)
        startsFrom.text="Available Starting times for "+intent.getStringExtra("startsFrom")

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager1 = LinearLayoutManager(this)
        amRecyclerView.layoutManager=linearLayoutManager
        pmRecyclerView.layoutManager=linearLayoutManager1
        presenter= BookingPresenter()
        RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
        var cal:Calendar= Calendar.getInstance()
        cal.timeInMillis=timeStamp.toLong()*1000
        if(intent.getStringExtra("from")!=null){
            if(timeStamp.toLong()*1000<System.currentTimeMillis()){
                if(intent.hasExtra("login")){

                }else if(intent.hasExtra("reshedule")){
                    cardCnf.visibility = View.VISIBLE
                    cnfImg.visibility = View.GONE
                    cnfBook.setText("Reschedule")
                    cnfBook.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.btn_yellow))
                }else {
                    cardCnf.visibility=View.GONE
                    cnfImg.visibility=View.VISIBLE
                }
            }else{
                if(intent.hasExtra("login")){

                }else if(intent.hasExtra("reshedule")){
                    cardCnf.visibility = View.VISIBLE
                    cnfImg.visibility = View.GONE
                    cnfBook.setText("Reschedule")
                    cnfBook.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.btn_yellow))
                }else {
                    cardCnf.visibility = View.VISIBLE
                    cnfImg.visibility = View.GONE
                }
            }
            proCal.state().edit().setMinimumDate(cal).commit()
            proCal.setSelectedDate(cal)
            presenter!!.getInstance(this@BookingActivity,amRecyclerView,pmRecyclerView,0,cardCnf,cnfImg)
        }else{
            cardCnf.visibility=View.GONE
            cnfImg.visibility=View.VISIBLE
            proCal.state().edit().setMinimumDate(cal).commit()
            proCal.setSelectedDate(cal)
            presenter!!.getInstance(this@BookingActivity,amRecyclerView,pmRecyclerView,timeStamp.toLong(),cardCnf,cnfImg)
        }
        var calendar:Calendar= proCal.selectedDate.calendar
        var startTime:Long
        var year: Int = calendar.get(Calendar.YEAR)
        var month: Int = calendar.get(Calendar.MONTH)
        var day: Int = calendar.get(Calendar.DATE)

        startTime = System.currentTimeMillis()/1000
        cal.set(year, month, day, 23, 0, 0)
        var endTime = (cal.timeInMillis) / 1000
        proCal.isDynamicHeightEnabled=true
        proCal.setOnMonthChangedListener { widget, date ->
            Log.e("ssads","dsss")
            widget.isDynamicHeightEnabled=true
        }
        val sdf2 = SimpleDateFormat("MMM yyyy")
        proCal.setTitleFormatter(DateFormatTitleFormatter(sdf2))
        presenter?.fetchAllAvailableSlot(email!!, startTime, endTime, 30, 30)
        proCal.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
            if(intent.hasExtra("login")){

            }else if(intent.hasExtra("reshedule")){
                cardCnf.visibility = View.VISIBLE
                cnfImg.visibility = View.GONE
                cnfBook.setText("Reschedule")
            }else {
                cardCnf.visibility = View.VISIBLE
                cnfImg.visibility = View.GONE
            }
            Log.e("sadasdasfs","assas"+date.calendar.time)
            startsFrom(date.calendar.timeInMillis)
            var calendar: Calendar = Calendar.getInstance()
            var year: Int = calendar.get(Calendar.YEAR)
            var month: Int = calendar.get(Calendar.MONTH)
            var day: Int = calendar.get(Calendar.DATE)

            var year1: Int = date.calendar.get(Calendar.YEAR)
            var month1: Int = date.calendar.get(Calendar.MONTH)
            var day1: Int = date.calendar.get(Calendar.DATE)
            val startTime:Long
            if (year == year1 && month == month1 && day == day1) {
                Log.e("calll","assIF")
                startTime = (System.currentTimeMillis())/1000
                date.calendar.set(year1, month1, day1, 23, 0, 0)
                var endTime = (date.calendar.timeInMillis) / 1000
                Log.e("asdsdsds", "asdas" + date.calendar.time)
                //  fetchAllAvailableSlot()
                presenter?.fetchAllAvailableSlot(email!!, startTime, endTime, 30, 30)
            } else {
                Log.e("calll","assELSE")
                date.calendar.set(year1, month1, day1, 9, 0, 0)
                startTime = (date.calendar.timeInMillis) / 1000
                date.calendar.set(year1, month1, day1, 23, 0, 0)
                var endTime = (date.calendar.timeInMillis) / 1000
                presenter?.fetchAllAvailableSlot(email!!, startTime, endTime, 30, 30)
            }
        })
        cnfImg.setOnClickListener {
            if(intent.hasExtra("login")){
                showCustomDialogSuccessBook()
            }else if(intent.hasExtra("reshedule")){

            }else{
                navigating(name, email)
            }
        }
        cardCnf.setOnClickListener {
            if(intent.hasExtra("login")){

            }else if(intent.hasExtra("reshedule")){
                showCustomDialogSuccess()
            }else{
                navigating(name, email)
            }
        }
        back.setOnClickListener {
            onBackPressed()
        }
    }

    lateinit var startTime: TextView
    lateinit var endTime: TextView
    lateinit var userName: TextView
    lateinit var mainTime: TextView
    lateinit var close: ImageView
    lateinit var done: ImageView
    private fun showCustomDialogSuccess() {
        val dialog = Dialog(this@BookingActivity)
        dialog.setContentView(R.layout.appointment_success_response)
        dialog.setTitle("Title...")
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.attributes?.windowAnimations = R.anim.fade_in
        userName = dialog.findViewById(R.id.name)
        startTime = dialog.findViewById(R.id.startTime)
        endTime = dialog.findViewById(R.id.endTime)
        mainTime = dialog.findViewById(R.id.mainTime)
        done = dialog.findViewById(R.id.done)
        close = dialog.findViewById(R.id.close)
        userName.text = /*name + ", " + */ "Manish, We've rescheduled your appointment"
        endTime.text = name

        getDateTime(timeStamp!!.toLong() * 1000, timeStamp1!!.toLong() * 1000)
        done.setOnClickListener {
            //close.performClick()
            startActivity(Intent(this@BookingActivity, HomeWithBottomTabsActivity::class.java))
        }
        close.setOnClickListener {
            startActivity(Intent(this@BookingActivity, HomeWithBottomTabsActivity::class.java))
        }
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        // set the custom dialog components - text, image and button
        dialog.show()
    }
    private fun getDateTime(s: Long, e: Long) {
        var date: Date = Date(s)
        val sdfMain = SimpleDateFormat("EEEE, MMM dd, yyyy zzzz (ZZZZ)")
        sdfMain.timeZone = TimeZone.getTimeZone("US/Mountain")
        //   sdfMain.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        val netDateMain = Date(e)
        mainTime.text = sdfMain.format(netDateMain)
        Log.e("Asda", "asda" + date)
        val sdf = SimpleDateFormat("hh:mm a")
        val netDate = Date(e)

        // endTime.text=sdf.format(netDate)
        val sdf1 = SimpleDateFormat("hh:mm a")
        val netDate1 = Date(s)
        startTime.text = sdf1.format(netDate1)
    }
    private fun showCustomDialogSuccessBook() {
        val dialog = Dialog(this@BookingActivity)
        dialog.setContentView(R.layout.appointment_success_response)
        dialog.setTitle("Title...")
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.attributes?.windowAnimations = R.anim.fade_in
        userName = dialog.findViewById(R.id.name)
        startTime = dialog.findViewById(R.id.startTime)
        endTime = dialog.findViewById(R.id.endTime)
        mainTime = dialog.findViewById(R.id.mainTime)
        done = dialog.findViewById(R.id.done)
        close = dialog.findViewById(R.id.close)
        userName.text = /*name + */"Manish, " + "We'have got you confirmed for your appointment"
        endTime.text = /*attorneyName*/"Donald Eby"

        getDateTime(timeStamp!!.toLong() * 1000, timeStamp1!!.toLong() * 1000)
        done.setOnClickListener {
            //close.performClick()
            dialog.dismiss()
           // startActivity(Intent(this@BookingActivity, MoreInfoActivity::class.java))
        }
        close.setOnClickListener {
            startActivity(Intent(this@BookingActivity, HomeWithBottomTabsActivity::class.java))
        }
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        // set the custom dialog components - text, image and button
        dialog.show()
    }

    fun getTimesSlot(startTime:Int,endTime:Int) {
        this.timeStamp=startTime
        this.timeStamp1=endTime
    }

    fun navigating(name:String,email:String){
        startActivity(Intent(this@BookingActivity, HomeWithBottomTabsActivity::class.java)
            .putExtra("startTime", timeStamp)
            .putExtra("endTime", timeStamp1)
            .putExtra("email", email)
            .putExtra("name", name))
    }

    fun startsFrom(s:Long){
        val sdf2 = SimpleDateFormat("dd MMM, yyyy")
        val netDate2 = Date(s)
        startsFromText = sdf2.format(netDate2)
        startsFrom.text="Available Starting times for "+startsFromText
    }
    override fun onRestart() {
        ProgressDialog.getInstance().hideProgress()
        timeStamp=intent.getIntExtra("startTime",0)
        timeStamp1=intent.getIntExtra("endTime",0)
        var email:String?=intent.getStringExtra("email")
        var name:String?=intent.getStringExtra("name")

        amRecyclerView=findViewById(R.id.amRecyclerView)
        pmRecyclerView=findViewById(R.id.pmRecyclerView)
        cnfBook=findViewById(R.id.cnfBook)
        cnfImg=findViewById(R.id.cnfImg)
        cardCnf=findViewById(R.id.cardCnf)
        back=findViewById(R.id.img_back)
        startsFrom=findViewById(R.id.startsFrom)
        proCal=findViewById(R.id.proCal)

        startsFrom.text="Available Starting times for "+intent.getStringExtra("startsFrom")

        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager1 = LinearLayoutManager(this)
        amRecyclerView.layoutManager=linearLayoutManager
        pmRecyclerView.layoutManager=linearLayoutManager1
        presenter= BookingPresenter()
        RetrofitCallbacks.getInstace().initializeServerInterface(presenter)
        var cal:Calendar= Calendar.getInstance()
        cal.timeInMillis=timeStamp.toLong()*1000
        if(intent.getStringExtra("from")!=null){
            cardCnf.visibility=View.VISIBLE
            cnfImg.visibility=View.GONE
            proCal.state().edit().setMinimumDate(cal).commit()
            proCal.setSelectedDate(cal)
            presenter!!.getInstance(this@BookingActivity,amRecyclerView,pmRecyclerView,0,cardCnf,cnfImg)
        }else{
            cardCnf.visibility=View.GONE
            cnfImg.visibility=View.VISIBLE
            proCal.state().edit().setMinimumDate(cal).commit()
            proCal.setSelectedDate(cal)
            presenter!!.getInstance(this@BookingActivity,amRecyclerView,pmRecyclerView,timeStamp.toLong(),cardCnf,cnfImg)
        }

        var calendar:Calendar= proCal.selectedDate.calendar
        var startTime:Long
        var year: Int = calendar.get(Calendar.YEAR)
        var month: Int = calendar.get(Calendar.MONTH)
        var day: Int = calendar.get(Calendar.DATE)
        startTime = System.currentTimeMillis()/1000
        cal.set(year, month, day, 23, 0, 0)
        var endTime = (cal.timeInMillis) / 1000
        proCal.isDynamicHeightEnabled=true
        proCal.setOnMonthChangedListener { widget, date ->
            Log.e("ssads","dsss")
            widget.isDynamicHeightEnabled=true
        }
        val sdf2 = SimpleDateFormat("MMM yyyy")
        proCal.setTitleFormatter(DateFormatTitleFormatter(sdf2))
        presenter?.fetchAllAvailableSlot(email!!, startTime, endTime, 30, 30)
        proCal.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
            cardCnf.visibility=View.VISIBLE
            cnfImg.visibility=View.GONE
            Log.e("sadasdasfs","assas"+date.calendar.time)
            startsFrom(date.calendar.timeInMillis)
            var calendar: Calendar = Calendar.getInstance()
            var year: Int = calendar.get(Calendar.YEAR)
            var month: Int = calendar.get(Calendar.MONTH)
            var day: Int = calendar.get(Calendar.DATE)
            var year1: Int = date.calendar.get(Calendar.YEAR)
            var month1: Int = date.calendar.get(Calendar.MONTH)
            var day1: Int = date.calendar.get(Calendar.DATE)
            val startTime:Long
            if (year == year1 && month == month1 && day == day1) {
                Log.e("calll","assIF")
                startTime = (System.currentTimeMillis())/1000
                date.calendar.set(year1, month1, day1, 23, 0, 0)
                var endTime = (date.calendar.timeInMillis) / 1000
                Log.e("asdsdsds", "asdas" + date.calendar.time)
                //  fetchAllAvailableSlot()
                presenter?.fetchAllAvailableSlot(email!!, startTime, endTime, 30, 30)
            } else {
                Log.e("calll","assELSE")
                date.calendar.set(year1, month1, day1, 9, 0, 0)
                startTime = (date.calendar.timeInMillis) / 1000
                date.calendar.set(year1, month1, day1, 23, 0, 0)
                var endTime = (date.calendar.timeInMillis) / 1000
                presenter?.fetchAllAvailableSlot(email!!, startTime, endTime, 30, 30)
            }
        })
        super.onRestart()
    }
}
