package com.organisation.healthapp.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.organisation.healthapp.R
import com.organisation.healthapp.helperclass.Formatter
import java.util.*

class PatientsListing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_listing)

        val formatter = Formatter()
        formatter.customBottomNavigation(this)

    }
}