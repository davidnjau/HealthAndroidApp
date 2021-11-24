package com.organisation.healthapp.patient

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.organisation.healthapp.R
import com.organisation.healthapp.helperclass.CustomDialogToast
import com.organisation.healthapp.helperclass.Formatter
import com.organisation.healthapp.helperclass.PatientListingAdapter
import com.organisation.healthapp.retrofit.RetrofitCallsAuthentication
import kotlinx.android.synthetic.main.activity_patients_listing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class PatientsListing : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private var retrofitCallsAuthentication: RetrofitCallsAuthentication = RetrofitCallsAuthentication()
    private lateinit var tvRegistrationDate : TextView
    private var dateRegistration : Date? = null

    private var dateRegistration1 : String = ""
    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    val formatter = Formatter()
    var customDialogToast = CustomDialogToast()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients_listing)

        formatter.customBottomNavigation(this)

        tvRegistrationDate = findViewById(R.id.tvRegistrationDate)
        tvRegistrationDate.setOnClickListener { showDatePicker() }

        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        relSearchList.setOnClickListener {

            if (dateRegistration != null){

                val millis = dateRegistration!!.time

                getListing(millis.toString())
                customDialogToast.CustomDialogToast(
                    this,
                    "Please wait as we get you data."
                )
            }else{
                customDialogToast.CustomDialogToast(
                    this,
                    "Please choose a date."
                )
            }

        }
    }

    private fun showDatePicker(){
        // Get Current Date
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]


        val datePickerDialog = DatePickerDialog(this,
            { _, year, monthOfYear, dayOfMonth ->

                val selectedDate = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                val date = formatter.changeDateFormat(selectedDate)
                val dateStr = formatter.changeDateTimeFormat(selectedDate)
                dateRegistration = date
                dateRegistration1 = selectedDate
                tvRegistrationDate.text = selectedDate

            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()
    }

    override fun onStart() {
        super.onStart()

        getListing("")

    }

    private fun getListing(date: String) {

        CoroutineScope(Dispatchers.IO).launch {

            val patientListingList = retrofitCallsAuthentication.getPatientList(this@PatientsListing,date)

            CoroutineScope(Dispatchers.Main).launch {
                val patientsListingAdapter = PatientListingAdapter(
                    patientListingList,this@PatientsListing)
                recyclerView.adapter = patientsListingAdapter
            }

        }
    }

}