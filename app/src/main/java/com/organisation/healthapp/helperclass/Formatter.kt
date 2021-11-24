package com.organisation.healthapp.helperclass

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.organisation.healthapp.MainActivity
import com.organisation.healthapp.R
import com.organisation.healthapp.auth.Profile

class Formatter {

    private lateinit var context: Context

    fun customBottomNavigation(context1: Context){
        context = context1
        val bottomNavigation = (context1 as Activity).findViewById<View>(R.id.bottom_navigation)
        val bottomNavigationView : BottomNavigationView = bottomNavigation.findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

    }
    private var navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_settings -> {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_home -> {

                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    val intent = Intent(context, Profile::class.java)
                    context.startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

}