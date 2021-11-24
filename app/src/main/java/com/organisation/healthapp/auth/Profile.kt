package com.organisation.healthapp.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.organisation.healthapp.R
import com.organisation.healthapp.helperclass.Formatter
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {
    private lateinit var formatter : Formatter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        formatter = Formatter()
        formatter.customBottomNavigation(this)
    }

    override fun onStart() {
        super.onStart()

        val successLogin = formatter.getUserDetails(this)
        if (successLogin != null){
            tvMainName.text = successLogin.fullNames
            tvViewProfile.text = successLogin.emailAddress
        }

    }
}