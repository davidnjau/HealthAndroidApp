package com.organisation.healthapp.helperclass

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.organisation.healthapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.HashMap

class PatientListingAdapter(
    private var patientListingList: List<PatientListingData>,
    private val context: Context
) :
    RecyclerView.Adapter<PatientListingAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val tvPatientName : TextView = itemView.findViewById(R.id.tvPatientName)
        val tvAge : TextView = itemView.findViewById(R.id.tvAge)
        val tvBMIValue : TextView = itemView.findViewById(R.id.tvBMIValue)

        init {



        }

        override fun onClick(p0: View?) {

        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Pager2ViewHolder {
        return Pager2ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.patient_listing,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {

        val userName = patientListingList[position].userName
        val age = patientListingList[position].age.toString()
        val bmiValue = patientListingList[position].bmiValue.toInt().toString()

        holder.tvPatientName.text = userName
        holder.tvAge.text = age
        holder.tvBMIValue.text = bmiValue


    }

    override fun getItemCount(): Int {
        return patientListingList.size
    }

}