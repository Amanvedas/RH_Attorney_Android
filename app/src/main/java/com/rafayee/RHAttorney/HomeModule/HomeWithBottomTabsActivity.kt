package com.rafayee.RHAttorney.HomeModule

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Handler
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.mikhaellopez.circularimageview.CircularImageView
import com.rafayee.RHAttorney.Forgot.View.ForgotActivity
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.Login.Model.LoginResponseModel
import com.rafayee.RHAttorney.Login.View.LoginActivity
import com.rafayee.RHAttorney.MenuModule.NotificationsActivity
import com.rafayee.RHAttorney.MenuModule.Presenter.AlertDialogPresenter
import com.rafayee.RHAttorney.MenuModule.ProfileActivity
import com.rafayee.RHAttorney.MenuModule.TermsAndConditionsActivity
import com.rafayee.RHAttorney.MenuModule.View.IUpdate
import com.rafayee.RHAttorney.MenuModule.View.UpdatePasswordActivity
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.RetrofitCallbacks
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection
import com.rafayee.RHAttorney.ServerConnections.SessionManager
import com.rafayee.RHAttorney.Utils.BiometricUtils
import com.rafayee.RHAttorney.Utils.FocusChangeListener
import com.vedas.apna.ServerConnections.AppStatus
import me.ibrahimsn.lib.SmoothBottomBar
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.util.regex.Pattern


class HomeWithBottomTabsActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener, IUpdate, FragmentInteractionListener,
    RetrofitCallbacks.ServerResponseInterface {
    private lateinit var navController: NavController
    private lateinit var smoothBottomBar: SmoothBottomBar
    lateinit var imgToggle: ImageView
    lateinit var nav_pic: CircularImageView
    lateinit var img_cancel: ImageView
    lateinit var notify: ImageView
    lateinit var txt_name: TextView
    lateinit var txtTitle:TextView
    lateinit var toolbar:RelativeLayout
    lateinit var hView: View
    lateinit var strFirstLetter : String
    lateinit var strSecondLetter :String
    lateinit var txtRound : TextView
    private lateinit var mDrawerLayout: DrawerLayout
    var signin: Boolean = false
    lateinit var alertDialogPresenter: AlertDialogPresenter
    lateinit var myMenu: Menu
    lateinit var profileIcon: CircularImageView
    private var doubleBackToExitPressedOnce = false
    lateinit var back: ImageView
    lateinit var  loginResponseModel: LoginResponseModel

    var cameraBottomSheetDialog: Dialog? = null
    var finger: Boolean = false
    private lateinit var cancellationSignal: CancellationSignal
    val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    showEnableSuccess(errString.toString(),finger)
                    //cameraBottomSheetDialog.dismiss()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    //cameraBottomSheetDialog!!.dismiss()
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_with_bottom_tabs)
        BiometricUtils.checkBiometricSupport(this@HomeWithBottomTabsActivity)
        showbottomDialog()
        initVar()
    }

    private fun showbottomDialog() {
        if (preferenceFileExist("LoginPref")){
            if(getSharedPreferences("LoginPref", 0).contains("enableTouchID")&&
                getSharedPreferences("LoginPref", 0).contains("enableFaceID")){
                if (getSharedPreferences("LoginPref", 0).getBoolean("enableTouchID",false) && BiometricUtils.splash_fingerface){
                    BiometricUtils.splash_fingerface = false
                    this.finger = true
                    if (finger) {
                        finger(this@HomeWithBottomTabsActivity)
                    }else{
                        face(this@HomeWithBottomTabsActivity)
                    }
                    //bottomSheet(this@MainActivity, true)
                }else if (getSharedPreferences("LoginPref", 0).getBoolean("enableFaceID",false) && BiometricUtils.splash_fingerface){
                    BiometricUtils.splash_fingerface = false
                    this.finger = false
                    if (finger) {
                        finger(this@HomeWithBottomTabsActivity)
                    }else {
                        face(this@HomeWithBottomTabsActivity)
                    }
                }
            }
        }
    }
    private fun preferenceFileExist(fileName: String): Boolean {
        val f = File("/data/data/" + getPackageName() + "/shared_prefs/"
                + fileName + ".xml");
        return f.exists()
    }

    @SuppressLint("NewApi")
    fun finger(context: Context/*, cameraBottomSheetDialog: BottomSheetDialog*/) {
        //this.cameraBottomSheetDialog = cameraBottomSheetDialog
        val biometricPrompt = BiometricPrompt.Builder(context)
            .setTitle("Login to R&H\n")
            .setSubtitle("Touch ID")
            .setDescription("Touch the fingerprint sensor")
            .setNegativeButton("Cancel", context.mainExecutor,
                DialogInterface.OnClickListener { dialog, which ->
                    showEnableSuccess("Authentication failed",true) }).build()

        biometricPrompt.authenticate(
            getCancellationSignal(),
            context.mainExecutor,
            authenticationCallback
        )
    }
    @SuppressLint("NewApi")
    fun face(context: Context/*, cameraBottomSheetDialog: BottomSheetDialog*/) {
        //this.cameraBottomSheetDialog = cameraBottomSheetDialog
        val biometricPrompt = BiometricPrompt.Builder(context)
            .setTitle("Login to R&H\n")
            .setSubtitle("Face ID")
            .setDescription("Look directly at your front camera")
            .setNegativeButton("Cancel", context.mainExecutor,
                DialogInterface.OnClickListener { dialog, which ->
                    showEnableSuccess("Authentication failed",false)
                }).build()
        biometricPrompt.authenticate(
            getCancellationSignal(),
            context.mainExecutor,
            authenticationCallback
        )
    }
    @RequiresApi(Build.VERSION_CODES.P)
    fun showEnableSuccess(error:String, finger: Boolean) {
        val dialog = Dialog(this@HomeWithBottomTabsActivity)
        dialog.setContentView(R.layout.enable_ids_dialog)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        var dontAllow: ImageView
        var ok: ImageView
        var img_error: ImageView
        var headingText: TextView
        var txt_errorr: TextView
        var li_error : LinearLayout

        dontAllow = dialog.findViewById(R.id.dontAllow)
        ok = dialog.findViewById(R.id.ok)
        headingText = dialog.findViewById(R.id.heading)
        li_error = dialog.findViewById(R.id.li_error)
        img_error = dialog.findViewById(R.id.error)
        txt_errorr = dialog.findViewById(R.id.txt_errorr)
        li_error.visibility = View.VISIBLE
        dontAllow.setImageDrawable(resources.getDrawable(R.drawable.cancel_btn))
        ok.setImageDrawable(resources.getDrawable(R.drawable.tryagain))
        headingText.text = error

        dontAllow.setOnClickListener {
            //dialog.dismiss()
            //cameraBottomSheetDialog?.dismiss()
            if (dontAllow.getDrawable().getConstantState() == getResources().getDrawable( R.drawable.ok).getConstantState()){
                dialog.dismiss()
                bottomSheet(this@HomeWithBottomTabsActivity)
                /*finishAffinity()
                System.exit(0)*/
            }else {
                if(getSharedPreferences("LoginPref", 0).getString("register_type","").toString().equals("Manual")) {
                    if (finger) {
                        headingText.text =
                            "Without fingerprint access we can't move to next.\n" +
                                    "If click on OK by using password to login"
                    } else {
                        headingText.text =
                            "Without faceID access we can't move to next.\n" +
                                    "If click on OK by using password to login"
                    }
                    dontAllow.setImageDrawable(resources.getDrawable(R.drawable.ok))
                }else{
                    txt_errorr.text = "Your R&H app is locked"
                    headingText.text = "Please close the app and open it again to unlock"
                    dontAllow.visibility = View.GONE
                    ok.visibility = View.GONE
                    img_error.visibility = View.GONE
                }
            }
            //Toast.makeText(this@MainActivity,"Without fingerprint access we can't move to next (or) use password",Toast.LENGTH_SHORT).show()
//            finishAffinity()
//            System.exit(0)
        }

        ok.setOnClickListener {
            dialog.dismiss()
            if (finger) {
                finger(this@HomeWithBottomTabsActivity)
            } else {
                face(this@HomeWithBottomTabsActivity)
            }
        }
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }
    fun isValidPasword(password: String?): Boolean {
        val expression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@!%*?&#])(?=\\S+$)[A-Za-z\\d$@!%*?&#]{8,13}"
        val pattern = Pattern.compile(expression)
        return !TextUtils.isEmpty(password) && pattern.matcher(password).matches()
    }
    fun bottomSheet(context: Context) {
        cameraBottomSheetDialog = Dialog(this@HomeWithBottomTabsActivity)
        cameraBottomSheetDialog!!.setContentView(R.layout.mainpage_finger_bottom_sheet)

        val cardPwd: TextInputLayout = cameraBottomSheetDialog!!.findViewById(R.id.cardPwd)
        val pwd: TextInputEditText = cameraBottomSheetDialog!!.findViewById(R.id.pwd)
        val login:ImageView = cameraBottomSheetDialog!!.findViewById(R.id.login)
        val forgot: TextView = cameraBottomSheetDialog!!.findViewById(R.id.forgot)

        FocusChangeListener(context, cardPwd, pwd, 0, 0, 10, 5, 5, 5, 10, 5)
        login.setOnClickListener {
            if (pwd.text?.trim()?.isNotEmpty()!!) {
                if (isValidPasword(pwd.text.toString())){
                    if (AppStatus.getInstance(context).isConnected()) {
                        ProgressDialog.getInstance().showProgress(context)
                        Handler().postDelayed({
                            if (getSharedPreferences("LoginPref", 0).getString("password","") .equals(pwd.text.toString())){
                                ProgressDialog.getInstance().hideProgress()
                                cameraBottomSheetDialog!!.dismiss()
                            }else{
                                ProgressDialog.getInstance().hideProgress()
                                Toast.makeText(context, "Login failed, password is wrong", Toast.LENGTH_SHORT).show()
                            }
                        }, 1000)
                    } else {
                        Toast.makeText(context, "No Internet Connection!!!!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    pwd.requestFocus()
                    Toast.makeText(context, "Password length must be minimum 8 and maximum 13 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number, 1 Special Character and Spaces not allowed.", Toast.LENGTH_SHORT).show()
                }
            } else {
                pwd.requestFocus()
                Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show()
            }
        }
        forgot.setOnClickListener {
            cameraBottomSheetDialog!!.dismiss()
            finishAffinity()
            context.startActivity(Intent(context, ForgotActivity::class.java))
        }
        cameraBottomSheetDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        cameraBottomSheetDialog!!.setCancelable(false)
        cameraBottomSheetDialog!!.setCanceledOnTouchOutside(false)
        cameraBottomSheetDialog!!.show()
    }
    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal.setOnCancelListener {
            Toast.makeText(this@HomeWithBottomTabsActivity,"Authentication is cancelled by user",Toast.LENGTH_SHORT).show()
        }
        return cancellationSignal
    }


    private fun initVar() {
        mDrawerLayout = findViewById(R.id.drawer_layout)
        imgToggle = findViewById(R.id.menu_btn)
        profileIcon = findViewById(R.id.profileIcon)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        notify = findViewById(R.id.notify)
        back = findViewById(R.id.back_btn)
        toolbar = findViewById(R.id.toolbar)
        txtTitle = findViewById(R.id.txt_title)
        alertDialogPresenter = AlertDialogPresenter()
        loginResponseModel = Gson().fromJson(getSharedPreferences("LoginPref", 0).getString("userInfo", ""),
            object : TypeToken<LoginResponseModel>(){}.type)
        alertDialogPresenter.AlertDialogPresenter(this)
        val drawerToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.setHomeAsUpIndicator(R.drawable.meni_white)
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setHomeButtonEnabled(true)
        mDrawerLayout.addDrawerListener(drawerToggle)
        imgToggle.setOnClickListener(View.OnClickListener {
            mDrawerLayout.openDrawer(Gravity.LEFT)
        })

        Glide.with(this)
            .load(ServerApiCollection.IMAGE_URL+ loginResponseModel.attorneysList.get(0).profilePic)
            .placeholder(R.drawable.profile_ic)
            .into(profileIcon)

        notify.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))

        })
        profileIcon.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))

        })

        myMenu = navigationView.menu
        navigationView.setNavigationItemSelectedListener(this);
        hView = navigationView.getHeaderView(0)
        txtRound = findViewById(R.id.txt_round)
        nav_pic = hView.findViewById<View>(R.id.img_pic) as CircularImageView
        txt_name = hView.findViewById<View>(R.id.txt_name) as TextView
        img_cancel = hView.findViewById<View>(R.id.img_cancel) as ImageView
        if (!loginResponseModel.attorneysList.get(0).profilePic.isEmpty()){
            Glide.with(this)
                .load(ServerApiCollection.IMAGE_URL+loginResponseModel.attorneysList.get(0).profilePic)
                .placeholder(R.drawable.profile_ic)
                .into(nav_pic)
            txtRound.visibility=View.GONE
        }else{
            txtRound.visibility=View.VISIBLE
            if(loginResponseModel.attorneysList.get(0).lastName.isEmpty()){
                strFirstLetter = loginResponseModel.attorneysList.get(0).firstName.take(1)
                strSecondLetter = ""
                txtRound.text=strFirstLetter+strSecondLetter
            }else{
                strFirstLetter =loginResponseModel.attorneysList.get(0).firstName.take(1)
                strSecondLetter =loginResponseModel.attorneysList.get(0).lastName.take(1)
                txtRound.text=strFirstLetter+strSecondLetter

            }
        }
        txt_name.text=loginResponseModel.attorneysList.get(0).firstName+" "+loginResponseModel.attorneysList.get(0).lastName
        navController = findNavController(R.id.main_fragment)
        smoothBottomBar = findViewById(R.id.bottomBar)
        setupActionBarWithNavController(navController)
        supportActionBar?.hide()
        img_cancel.setOnClickListener {
            mDrawerLayout.closeDrawers()
        }

        back.setOnClickListener {
            onBackPressed()
        }


        navigationView.setNavigationItemSelectedListener(this)

        smoothBottomBar.onItemSelected = {
            when {
                it == 0 -> {
                    imgToggle.visibility = View.VISIBLE
                    back.visibility = View.GONE
                    txtTitle.text = "Home"
                    toolbar.visibility = View.VISIBLE
                }
                it == 1 -> {
                    imgToggle.visibility = View.VISIBLE
                    back.visibility = View.GONE
                    txtTitle.text = "Firm Clients"
                    toolbar.visibility = View.VISIBLE
                }
                it == 2 -> {
                    imgToggle.visibility = View.VISIBLE
                    back.visibility = View.GONE
                    toolbar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                mDrawerLayout.closeDrawers()
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            R.id.nav_notification -> {
                mDrawerLayout.closeDrawers()
                startActivity(Intent(this, NotificationsActivity::class.java))
            }
            R.id.nav_terms -> {
                mDrawerLayout.closeDrawers()
                startActivity(Intent(this, TermsAndConditionsActivity::class.java).putExtra("terms","terms"))
            }
            R.id.nav_policy -> {
                mDrawerLayout.closeDrawers()
                startActivity(Intent(this, TermsAndConditionsActivity::class.java).putExtra("terms","privacy"))
            }
            R.id.nav_contact -> {
                mDrawerLayout.closeDrawers()
                startActivity(Intent(this, TermsAndConditionsActivity::class.java))
            }
            R.id.nav_tech -> {
                mDrawerLayout.closeDrawers()
                alertDialogPresenter.techSupportAlert()
            }
            R.id.nav_update -> {
                mDrawerLayout.closeDrawers()
                startActivity(Intent(this, UpdatePasswordActivity::class.java))
            }
            R.id.nav_sign -> {
                mDrawerLayout.closeDrawers()
                alertDialogPresenter.logoutAlertDialogg(this)
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_menu, menu)
        smoothBottomBar.setupWithNavController(menu!!, navController)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        //onBackPressed()
        return true
    }

    override fun finishView() {
        if (AppStatus.getInstance(this@HomeWithBottomTabsActivity).isConnected()) {
            RetrofitCallbacks.getInstace().initializeServerInterface(this@HomeWithBottomTabsActivity)
            ProgressDialog.getInstance().showProgress(this@HomeWithBottomTabsActivity)
            logOut(this@HomeWithBottomTabsActivity,getSharedPreferences("LoginPref", 0).getString("emailID","").toString())
        } else {
            Toast.makeText(this, "No Internet Connection!!!!", Toast.LENGTH_SHORT).show()
        }
    }
   /* override fun finishView() {
        startActivity(Intent(this@HomeWithBottomTabsActivity, LoginActivity::class.java))
        finishAffinity()
    }*/

    override fun onClickButton(fragment: Fragment?) {
        if ((navController.getCurrentDestination()!!.id == R.id.first_fragment)) {
            // handle back button the way you want here
            imgToggle.visibility = View.VISIBLE
            back.visibility = View.GONE
            txtTitle.text = "Home"
            toolbar.visibility = View.VISIBLE
        }else if (navController.getCurrentDestination()!!.id == R.id.second_fragment) {
            Log.e("checal","da")
            imgToggle.visibility = View.VISIBLE
            back.visibility = View.GONE
            txtTitle.text = "Firm Clients"
            toolbar.visibility = View.VISIBLE
        }else if (navController.getCurrentDestination()!!.id == R.id.thired_fragment) {
            imgToggle.visibility = View.GONE
            back.visibility = View.VISIBLE
            txtTitle.text = "Client Info"
            toolbar.visibility = View.VISIBLE
        }else if (navController.getCurrentDestination()!!.id == R.id.client_info) {
            imgToggle.visibility = View.GONE
            back.visibility = View.VISIBLE
            toolbar.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        if ((navController.getCurrentDestination()!!.id == R.id.first_fragment)) {
            // handle back button the way you want here
            imgToggle.visibility = View.VISIBLE
            back.visibility = View.GONE
            txtTitle.text = "Home"
            toolbar.visibility = View.VISIBLE
        }else if( (navController.getCurrentDestination()!!.id == R.id.second_fragment) ){
            imgToggle.visibility = View.VISIBLE
            back.visibility = View.GONE
            toolbar.visibility = View.VISIBLE
        } else if (navController.getCurrentDestination()!!.id == R.id.thired_fragment) {
            imgToggle.visibility = View.GONE
            back.visibility = View.VISIBLE
            txtTitle.text = "Firm Clients"
            imgToggle.visibility = View.VISIBLE
            back.visibility = View.GONE
            toolbar.visibility = View.VISIBLE
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment)
        val backStackEntryCount = navHostFragment?.childFragmentManager?.backStackEntryCount
        Log.e("supportt", " backpress" + backStackEntryCount)
        if (backStackEntryCount != null) {
            if (backStackEntryCount > 0) {
                Log.e("supportt", " backpress12")

                if (!(navController.getCurrentDestination()!!.id == R.id.thired_fragment)) {
                    navController.popBackStack()
                    Log.e("supportt", " backpress13" + backStackEntryCount)
                    txtTitle.text = "Home"

                }else{
                    Log.e("supportt", " backpress11" + backStackEntryCount)
                   // super.onBackPressed()
                    navController.popBackStack()
                }
                // super.onBackPressed();
                // return;
            } else {
                if (doubleBackToExitPressedOnce) {
                    finishAffinity();
                   // super.onBackPressed()
                   // return
                }
                doubleBackToExitPressedOnce = true
                Toast.makeText(
                    this, "Press one more time to exit",
                    Toast.LENGTH_SHORT
                ).show()
                Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 3000)
            }
        }
        //super.onBackPressed()
    }
    private fun logOut(context: Context, Email: String) {
        var regObj = JsonObject()
        val jsonObject = JSONObject()
        val token = FirebaseInstanceId.getInstance().token
        Log.e("gjbjh", "jhk:$token")
        val ID = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        try {
            jsonObject.put("emailID", Email)
            jsonObject.put("deviceID", ID)
            jsonObject.put("deviceToken", token)
            jsonObject.put("deviceType", "Mobile")
            val jsonParser = JsonParser()
            regObj = jsonParser.parse(jsonObject.toString()) as JsonObject
            //print parameter
            Log.e("RegisterJSON:", " $regObj")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        RetrofitCallbacks.getInstace().apiCallBacks(context, "attorney/signout",regObj,"signout")
    }


    override fun failureCallBack(failureMsg: String?) {
        Toast.makeText(this, failureMsg, Toast.LENGTH_SHORT).show()
        ProgressDialog.getInstance().hideProgress()
    }

    override fun successCallBack(body: String?, from: String?) {
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(body)
            if (jsonObject.getString("response").equals("3")){
                if (SessionManager(this@HomeWithBottomTabsActivity).isLoggedIn()) {
                    SessionManager(this@HomeWithBottomTabsActivity).logoutUser()
                }
                Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
       /*
        if (from.equals("signOut")){
            progressDialog.hideProgress()
            SP = getSharedPreferences(filename, 0);
            editit = SP!!.edit()
            editit.clear()
            editit.apply()
            startActivity(Intent(this, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))

        }
        progressDialog.hideProgress()
        SP = getSharedPreferences(filename, 0);
        editit = SP!!.edit()
        editit.clear()
        editit.apply()
        startActivity(Intent(this, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))*/

    }
}
