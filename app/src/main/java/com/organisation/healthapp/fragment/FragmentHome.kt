package com.organisation.healthapp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.organisation.healthapp.R
import com.organisation.healthapp.helperclass.Formatter
import com.organisation.healthapp.patient.PatientsListing
import com.organisation.healthapp.replaceFragmenty
import com.organisation.healthapp.retrofit.RetrofitCallsAuthentication
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set


class FragmentHome : Fragment() {

    private lateinit var formatter : Formatter
    private var retrofitCallsAuthentication: RetrofitCallsAuthentication = RetrofitCallsAuthentication()

    private lateinit var tvPatientReg:TextView
    private lateinit var tvPatientReg1:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        tvPatientReg = rootView.findViewById(R.id.tvPatientReg)
        tvPatientReg1 = rootView.findViewById(R.id.tvPatientReg1)

        formatter = Formatter()

        rootView.tvPatientRegistration.setOnClickListener {

            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.mainContent, FragmentPatientRegistration())
            ft.addToBackStack(null)
            ft.commit()

        }
        rootView.tvVitalsForm.setOnClickListener {
            val ft = requireActivity().supportFragmentManager.beginTransaction()
            ft.replace(R.id.mainContent, FragmentPatientVitals())
            ft.addToBackStack(null)
            ft.commit()
        }
        rootView.tvPatientListing.setOnClickListener {
            val intent = Intent(requireActivity(), PatientsListing::class.java)
            startActivity(intent)
        }


        return rootView
    }

    override fun onStart() {
        super.onStart()

        val successLogin = formatter.getUserDetails(requireActivity())
        if (successLogin != null){
            tvUserName.text = successLogin.fullNames
        }

        setGreeting()

        getNumbers()

    }

    private fun getNumbers() {

        CoroutineScope(Dispatchers.IO).launch {
            val patientListingList = retrofitCallsAuthentication.getPatientList(requireActivity(),"")
            CoroutineScope(Dispatchers.Main).launch {
                val size = patientListingList.size
                tvPatientReg.text = size.toString()
                tvPatientReg1.text = size.toString()
            }
        }

    }

    private fun setGreeting() {

        val date = Date()
        val cal: Calendar = Calendar.getInstance()
        cal.time = date
        val hour: Int = cal.get(Calendar.HOUR_OF_DAY)

        //Set greeting

        //Set greeting
        var greeting: String = ""
        when (hour) {
            in 6..11 -> {
                greeting = "Good Morning,"
            }
            in 12..16 -> {
                greeting = "Good Afternoon,"
            }
            in 17..19 -> {
                greeting = "Good Evening,"
            }
            in 20..23 -> {
                greeting = "Good Night,"
            }
        }
        tvGreetings.text = greeting

    }


}