package com.organisation.healthapp

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.organisation.healthapp.auth.Profile
import com.organisation.healthapp.fragment.FragmentPatientRegistration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawer_layout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawer_layout = findViewById(R.id.drawer_layout)

        nav_view.setNavigationItemSelectedListener(this)

        toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)

        bottom_Nav_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
    }

    private var navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_settings -> {
                    openDrawer()

                }
                R.id.navigation_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_profile -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun openDrawer(){

        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }else{
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }
    override fun onStart() {
        super.onStart()

        replaceFragmenty(
            fragment = FragmentPatientRegistration(),
            allowStateLoss = true,
            containerViewId = R.id.mainContent
        )

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.navigation_home -> {

                replaceFragmenty(
                    fragment = FragmentPatientRegistration(),
                    allowStateLoss = true,
                    containerViewId = R.id.mainContent
                )

            }
//            R.id.navigation_orders -> {
//
//                replaceFragmenty(
//                    fragment = FragmentOrders(),
//                    allowStateLoss = true,
//                    containerViewId = R.id.mainContent
//                )
//
//            }
//
//            R.id.navigation_settings -> {
//
//                replaceFragmenty(
//                    fragment = FragmentSettings(),
//                    allowStateLoss = true,
//                    containerViewId = R.id.mainContent
//                )
//
//            }



        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true


    }


    override fun onBackPressed() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }else
            super.onBackPressed()
    }

}