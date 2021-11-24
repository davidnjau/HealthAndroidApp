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
import com.organisation.healthapp.patient.PatientsListing
import com.organisation.healthapp.replaceFragmenty
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set


class FragmentHome : Fragment() {

    private lateinit var tvPatientRegistration: TextView
    private lateinit var tvVitalsForm: TextView
    private lateinit var tvPatientListing: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)


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




}