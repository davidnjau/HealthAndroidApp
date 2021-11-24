package com.organisation.healthapp.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.organisation.healthapp.R
import com.organisation.healthapp.helperclass.Formatter

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val formatter = Formatter()
        formatter.customBottomNavigation(this)
    }
}