package com.organisation.healthapp.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.organisation.healthapp.MainActivity
import com.organisation.healthapp.R
import com.organisation.healthapp.helperclass.Formatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set
import android.text.Editable
import android.text.TextUtils

import android.widget.Toast
import android.text.TextWatcher
import com.organisation.healthapp.helperclass.CustomDialogToast
import com.organisation.healthapp.helperclass.PatientsVitalsData
import com.organisation.healthapp.retrofit.RetrofitCallsAuthentication
import kotlinx.android.synthetic.main.fragment_patient_vitals.view.*

class FragmentPatientVitals : Fragment() {

    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private  var mHour:Int = 0
    private  var mMinute:Int = 0
    private var formatter = Formatter()
    private var patientId :String? = null
    private var patientFullNames :String? = null
    private var patientNumber :String? = null

    private lateinit var etPatientNumber: EditText
    private lateinit var tvRegistrationDate : TextView
    private var dateRegistration : Date? = null

    private var dateRegistration1 : String = ""

    private lateinit var etWeight: EditText
    private lateinit var etHeight: EditText
    private lateinit var tvBMIValue: TextView
    var customDialogToast = CustomDialogToast()
    private var retrofitCallsAuthentication: RetrofitCallsAuthentication = RetrofitCallsAuthentication()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_patient_vitals, container, false)

        etPatientNumber = rootView.findViewById(R.id.etPatientNumber)
        tvRegistrationDate = rootView.findViewById(R.id.tvRegistrationDate)
        tvRegistrationDate.setOnClickListener { showDatePicker() }

        etWeight = rootView.findViewById(R.id.etWeight)
        etHeight = rootView.findViewById(R.id.etHeight)
        tvBMIValue = rootView.findViewById(R.id.tvBMIValue)

        etWeight.addTextChangedListener(textWatcher)
        etHeight.addTextChangedListener(textWatcher)

        rootView.btnSave.setOnClickListener {

            val height = etHeight.text.toString()
            val weight = etWeight.text.toString()

            if (!TextUtils.isEmpty(height)
                && !TextUtils.isEmpty(weight)
                && dateRegistration != null){


                    if (patientId != null){
                        val registerPatient = PatientsVitalsData(patientId!!, dateRegistration1, weight.toDouble(), height.toDouble(), null)
                        retrofitCallsAuthentication.addPatientVitals(requireActivity(), registerPatient)
                    }




            }else{
                customDialogToast.CustomDialogToast(
                    requireActivity(),
                    "Please fill all the fields."
                )
            }

        }

        return rootView
    }

    var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            calculateBMI(s)
        }
    }

    private fun calculateBMI(s: Editable) {

        val height = etHeight.text.toString()
        val weight = etWeight.text.toString()

        if (!TextUtils.isEmpty(height) && !TextUtils.isEmpty(weight)){

            val bmiValue =formatter.calculateBMI(weight.toDouble(), height.toDouble()).toInt()
            tvBMIValue.text = bmiValue.toString()

        }else{

            if(TextUtils.isEmpty(height ) && TextUtils.isEmpty(weight))
                tvBMIValue.text = "0"


        }

    }

    override fun onStart() {
        super.onStart()

        getPatientDetails()

    }

    private fun showDatePicker(){
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
                dateRegistration = date
                dateRegistration1 = dateStr
                tvRegistrationDate.text = selectedDate

            },
            mYear,
            mMonth,
            mDay
        )
        datePickerDialog.show()
    }

    private fun getPatientDetails() {

        val patientDetails = formatter.getPatientDetails(requireActivity())
        val id = patientDetails.first
        val names = patientDetails.second
        val number = patientDetails.third
        if (id != null && names != null && number != null){

            patientId = id
            patientFullNames = names
            patientNumber = number

            etPatientNumber.setText(patientNumber)

        }else{
            val intent =Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }

    }


}