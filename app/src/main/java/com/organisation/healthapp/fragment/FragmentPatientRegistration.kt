package com.organisation.healthapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.organisation.healthapp.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.widget.*

import com.organisation.healthapp.helperclass.Formatter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_patient_reg.*

import java.lang.StringBuilder
import android.widget.ArrayAdapter
import com.organisation.healthapp.helperclass.CustomDialogToast
import com.organisation.healthapp.helperclass.PatientRegistrationData
import com.organisation.healthapp.retrofit.RetrofitCallsAuthentication
import kotlinx.android.synthetic.main.fragment_patient_reg.view.*
import java.text.SimpleDateFormat

class FragmentPatientRegistration : Fragment() , AdapterView.OnItemSelectedListener {

    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private  var mHour:Int = 0
    private  var mMinute:Int = 0

    private lateinit var spinner : Spinner
    private lateinit var tvRegistrationDate : TextView
    private lateinit var tvDOB : TextView
    private var formatter: Formatter = Formatter()

    private var dateRegistration : Date? = null
    private var dateOfBirth : Date? = null

    private var dateRegistration1 : String = ""
    private var dateOfBirth1 : String = ""

    private var selectedGender: String? = null
    var customDialogToast = CustomDialogToast()
    private var retrofitCallsAuthentication: RetrofitCallsAuthentication = RetrofitCallsAuthentication()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_patient_reg, container, false)

        spinner = rootView.findViewById(R.id.spinner)
        tvDOB = rootView.findViewById(R.id.tvDOB)
        tvRegistrationDate = rootView.findViewById(R.id.tvRegistrationDate)



        tvDOB.setOnClickListener { showDatePicker("dob") }
        tvRegistrationDate.setOnClickListener { showDatePicker("regDate") }

        spinner.onItemSelectedListener = this

        val categories: MutableList<String> = ArrayList()
        categories.add("MALE")
        categories.add("FEMALE")

        // Creating adapter for spinner
        val dataAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, categories)

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // attaching data adapter to spinner
        spinner.adapter = dataAdapter

        rootView.btnSave.setOnClickListener {

            val etPatientNumber = etPatientNumber.text.toString()
            val etFirstName = etFirstName.text.toString()
            val etLastName = etLastName.text.toString()

            if (!TextUtils.isEmpty(etPatientNumber)
                && !TextUtils.isEmpty(etFirstName)
                && !TextUtils.isEmpty(etLastName)
                && dateOfBirth != null
                && dateRegistration != null
                && selectedGender != null
            ){

                val registerPatientRegistration = PatientRegistrationData(null, etPatientNumber,
                    dateRegistration1, etFirstName, etLastName, dateOfBirth1, selectedGender!!)
                retrofitCallsAuthentication.addPatient(requireActivity(), registerPatientRegistration)

            }else{
                customDialogToast.CustomDialogToast(
                    requireActivity(),
                    "Please fill all the fields."
                )
            }

        }



        return rootView
    }

    private fun showDatePicker(value:String){
        // Get Current Date
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]


        val datePickerDialog = DatePickerDialog(requireActivity(),
            { _, year, monthOfYear, dayOfMonth ->

                val selectedDate = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                val date = formatter.changeDateFormat(selectedDate)
                val dateStr = formatter.changeDateTimeFormat(selectedDate)


                if (value == "dob"){
                    tvDOB.text = selectedDate
                    dateOfBirth = date
                    dateOfBirth1 = dateStr
                }
                if(value == "regDate"){
                    tvRegistrationDate.text = selectedDate
                    dateRegistration = date
                    dateRegistration1 = dateStr
                }
            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item = p0?.getItemAtPosition(p2).toString()
        selectedGender = item
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

}