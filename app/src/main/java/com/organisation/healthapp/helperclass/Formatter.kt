package com.organisation.healthapp.helperclass

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.organisation.healthapp.MainActivity
import com.organisation.healthapp.R
import com.organisation.healthapp.auth.Profile
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

    fun getUserDetails(context: Context):SuccessLogin?{

        val preferences = context.getSharedPreferences(
            context.resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val accessToken = preferences.getString("accessToken", null)
        val userId = preferences.getString("userId", null)
        val fullNames = preferences.getString("fullNames", null)
        val emailAddress = preferences.getString("emailAddress", null)

        return if (accessToken != null && userId != null && fullNames != null && emailAddress != null) {
            SuccessLogin(
                accessToken, userId, fullNames, emailAddress, ArrayList()
            )
        }else{
            null
        }


    }


    fun changeDateTimeFormat(dateStr: String): String {

        val originalFormat = SimpleDateFormat("dd-MM-yyyy")
        val targetFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val date = originalFormat.parse(dateStr)
        return targetFormat.format(date)
    }

    fun changeDateFormat(date: String): Date {
        val format = SimpleDateFormat("dd-MM-yyyy")
        return format.parse(date)
    }
    fun getHeaders(context: Context):HashMap<String, String>{

        val stringStringMap = HashMap<String, String>()

        val successLogin = getUserDetails(context)
        val accessToken = successLogin?.accessToken.toString()
        stringStringMap["Authorization"] = " Bearer $accessToken"

        return stringStringMap

    }

    fun getPatientDetails(context: Context):Triple<String?, String?, String?>{

        val preferences = context.getSharedPreferences(
            context.resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val patientId = preferences.getString("patientId", null)
        val patientFullNames = preferences.getString("patientFullNames", null)
        val patientNumber = preferences.getString("patientNumber", null)

        return if (patientFullNames != null && patientId != null){
            Triple(patientId, patientFullNames,patientNumber)
        }else{
            Triple(null, null, null)
        }

    }

    fun calculateBMI(weightKgs: Double, heightCm: Double): Double {

//        BMI = Weight(kg)/ Height(M)2 )

        val heightM = heightCm / 100
        val heightSquare = heightM * heightM

        return weightKgs / heightSquare

    }

}