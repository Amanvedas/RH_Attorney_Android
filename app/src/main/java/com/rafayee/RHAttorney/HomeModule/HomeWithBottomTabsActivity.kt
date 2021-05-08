package com.rafayee.RH.HomeModule

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import com.mikhaellopez.circularimageview.CircularImageView
import com.rafayee.RH.MenuModule.*
import com.rafayee.RH.MenuModule.Presenter.AlertDialogPresenter
import com.rafayee.RH.MenuModule.View.IUpdate
import com.rafayee.RH.MenuModule.View.UpdatePasswordActivity
import com.rafayee.RHAttorney.HomeModule.FragmentInteractionListener
import com.rafayee.RHAttorney.MainActivity
import com.rafayee.RHAttorney.MenuModule.ProfileActivity
import com.rafayee.RHAttorney.R
import me.ibrahimsn.lib.SmoothBottomBar


class HomeWithBottomTabsActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener, IUpdate, FragmentInteractionListener {
    private lateinit var navController: NavController
    private lateinit var smoothBottomBar: SmoothBottomBar
    lateinit var imgToggle: ImageView
    lateinit var nav_pic: CircularImageView
    lateinit var img_cancel: ImageView
    lateinit var notify: ImageView
    lateinit var txt_name: TextView
    lateinit var toolbar:RelativeLayout
    lateinit var hView: View
    private lateinit var mDrawerLayout: DrawerLayout
    var signin: Boolean = false
    lateinit var alertDialogPresenter: AlertDialogPresenter
    lateinit var myMenu: Menu
    lateinit var profileIcon: CircularImageView
    private var doubleBackToExitPressedOnce = false
    lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_with_bottom_tabs)
        initVar()
    }

    private fun initVar() {
        mDrawerLayout = findViewById(R.id.drawer_layout)
        imgToggle = findViewById(R.id.menu_btn)
        profileIcon = findViewById(R.id.profileIcon)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        notify = findViewById(R.id.notify)
        back = findViewById(R.id.back_btn)
        toolbar = findViewById(R.id.toolbar)

        alertDialogPresenter = AlertDialogPresenter()
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
        myMenu = navigationView.menu
        navigationView.setNavigationItemSelectedListener(this);
        hView = navigationView.getHeaderView(0)
        nav_pic = hView.findViewById<View>(R.id.img_pic) as CircularImageView
        txt_name = hView.findViewById<View>(R.id.txt_name) as TextView
        img_cancel = hView.findViewById<View>(R.id.img_cancel) as ImageView

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
                    toolbar.visibility = View.VISIBLE
                }
                it == 1 -> {
                    imgToggle.visibility = View.VISIBLE
                    back.visibility = View.GONE
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
                startActivity(Intent(this, TermsAndConditionsActivity::class.java))
            }
            R.id.nav_policy -> {
                mDrawerLayout.closeDrawers()
                startActivity(Intent(this, PolicyActivity::class.java))
            }
            R.id.nav_contact -> {
                mDrawerLayout.closeDrawers()
                startActivity(Intent(this, ContactUsActivity::class.java))
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
        startActivity(Intent(this@HomeWithBottomTabsActivity, MainActivity::class.java))
        finishAffinity()
    }

    override fun onClickButton(fragment: Fragment?) {

        if ((navController.getCurrentDestination()!!.id == R.id.first_fragment)) {
            // handle back button the way you want here
            imgToggle.visibility = View.VISIBLE
            back.visibility = View.GONE
            toolbar.visibility = View.VISIBLE
        }else if (navController.getCurrentDestination()!!.id == R.id.second_fragment) {
            imgToggle.visibility = View.VISIBLE
            back.visibility = View.GONE
            toolbar.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        if ((navController.getCurrentDestination()!!.id == R.id.first_fragment) ||
                (navController.getCurrentDestination()!!.id == R.id.second_fragment)

        ) {
            // handle back button the way you want here
            imgToggle.visibility = View.VISIBLE
            back.visibility = View.GONE
            toolbar.visibility = View.VISIBLE
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment)
        val backStackEntryCount = navHostFragment?.childFragmentManager?.backStackEntryCount
        Log.e("supportt", " backpress" + backStackEntryCount)
        if (backStackEntryCount != null) {
            if (backStackEntryCount > 0) {
               /* if (QuestionnairesFragment.newInstance("param1","param2").boolean){
                    navController.popBackStack()
                }*/

            } else {
                if (doubleBackToExitPressedOnce) {
                    navController.popBackStack()
                    super.onBackPressed()
                    return
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
}