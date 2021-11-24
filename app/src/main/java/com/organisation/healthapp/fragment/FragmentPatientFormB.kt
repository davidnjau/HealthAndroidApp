package com.organisation.healthapp.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.organisation.healthapp.MainActivity
import com.organisation.healthapp.R
import com.organisation.healthapp.helperclass.CustomDialogToast
import com.organisation.healthapp.helperclass.Formatter
import com.organisation.healthapp.helperclass.PatientsFormData
import com.organisation.healthapp.retrofit.RetrofitCallsAuthentication
import kotlinx.android.synthetic.main.fragment_patient_form_b.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set


class FragmentPatientFormB : Fragment() {

    private var formatter = Formatter()
    private var patientId :String? = null
    private var patientFullNames :String? = null
    private var patientNumber :String? = null
    private lateinit var etPatientNumber: EditText
    private lateinit var etComments: EditText

    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private lateinit var tvRegistrationDate : TextView
    private var dateRegistration : Date? = null

    private var dateRegistration1 : String = ""
    var customDialogToast = CustomDialogToast()
    private var retrofitCallsAuthentication: RetrofitCallsAuthentication = RetrofitCallsAuthentication()

    private lateinit var radioGrpGeneralHealth : RadioGroup
    private lateinit var radioGrpDrugs : RadioGroup

    private lateinit var radioButtonHealth : RadioButton
    private lateinit var radioButtonDrugs : RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_patient_form_b, container, false)


        etComments = rootView.findViewById(R.id.etComments)
        etPatientNumber = rootView.findViewById(R.id.etPatientNumber)
        tvRegistrationDate = rootView.findViewById(R.id.tvRegistrationDate)

        radioGrpGeneralHealth = rootView.findViewById(R.id.radioGrpGeneralHealth)
        radioGrpDrugs = rootView.findViewById(R.id.radioGrpDrugs)

        tvRegistrationDate.setOnClickListener { showDatePicker() }

        rootView.btnSave.setOnClickListener {

            val selectedOptionDrugs = radioGrpDrugs.checkedRadioButtonId
            radioButtonHealth = rootView.findViewById(selectedOptionDrugs)
            val healthValue = radioButtonHealth.text

            val selectedOptionWeight = radioGrpGeneralHealth.checkedRadioButtonId
            radioButtonDrugs = rootView.findViewById(selectedOptionWeight)
            val drugsValue = radioButtonDrugs.text


            val comments = etComments.text.toString()

            if (!TextUtils.isEmpty(comments)
                && dateRegistration != null
                && healthValue != null
                && drugsValue != null
            ){


                if (patientId != null){
                    val patientsFormData = PatientsFormData(patientId!!, dateRegistration1,
                        healthValue.toString(), null, drugsValue.toString(), comments)
                    retrofitCallsAuthentication.addPatientForm(requireActivity(), patientsFormData)
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

    override fun onStart() {
        super.onStart()

        getPatientDetails()

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


}