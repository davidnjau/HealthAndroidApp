package com.organisation.healthapp.retrofit

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.organisation.healthapp.MainActivity
import com.organisation.healthapp.R
import com.organisation.healthapp.auth.Login
import com.organisation.healthapp.helperclass.CustomDialogToast
import com.organisation.healthapp.helperclass.*
import com.organisation.healthapp.helperclass.Formatter
import com.organisation.healthapp.helperclass.UrlData
import com.organisation.healthapp.patient.PatientsListing
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList
import com.organisation.healthapp.helperclass.PatientListingData




class RetrofitCallsAuthentication {

    var customDialogToast = CustomDialogToast()

    fun registerUser(context: Context, userRegister: RegisterRequest){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                startRegistration(context, userRegister)
            }.join()
        }

    }
    private suspend fun startRegistration(
        context: Context,
        userRegister: RegisterRequest,
    ) {

        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Registration in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            var messageToast = ""
            val job = Job()

            CoroutineScope(Dispatchers.IO + job).launch {

                val baseUrl = context.getString(UrlData.BASE_URL.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)

                val apiInterface = apiService.registerUser(userRegister)
                apiInterface.enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {

                        CoroutineScope(Dispatchers.Main).launch { progressDialog.dismiss() }

                        if (response.isSuccessful) {

                            messageToast =
                                "You have been registered successfully. Check your email to verify the code."
                            customDialogToast.CustomDialogToast(context as Activity, messageToast)

                            val intent = Intent(context, Login::class.java)
                            context.startActivity(intent)

                        } else {

                            val code = response.code()
                            messageToast = if (code != 500){
                                val objectError = JSONObject(response.errorBody()!!.string())
                                val error = objectError.getString("error")
                                error
                            }else{
                                "There is something wrong. Please try again"
                            }

                            CoroutineScope(Dispatchers.IO).launch {

                                CoroutineScope(Dispatchers.Main).launch {
                                    customDialogToast.CustomDialogToast(
                                        context as Activity,
                                        messageToast
                                    )
                                }


                            }


                        }


                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Log.e("-*-*error ", t.localizedMessage)
                        messageToast = "There is something wrong. Please try again"
                        CoroutineScope(Dispatchers.Main).launch {
                            customDialogToast.CustomDialogToast(
                                context as Activity,
                                messageToast
                            )
                        }

                        progressDialog.dismiss()
                    }
                })

            }.join()


        }

    }


    fun loginUser(context: Context, userLogin: UserLogin){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                startLogin(context, userLogin)

            }.join()
        }

    }
    private suspend fun startLogin(context: Context, userLogin: UserLogin) {

        val preferences = context.getSharedPreferences(
            context.resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = preferences.edit()

        var isLoggedIn  = false
        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Login in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            var messageToast = ""
            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                val baseUrl = context.getString(UrlData.BASE_URL.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
                val apiInterface = apiService.loginUser(userLogin)
                apiInterface.enqueue(object : Callback<SuccessLogin> {
                    override fun onResponse(
                        call: Call<SuccessLogin>,
                        response: Response<SuccessLogin>
                    ) {

                        CoroutineScope(Dispatchers.Main).launch { progressDialog.dismiss() }

                        if (response.isSuccessful) {
                            isLoggedIn = true
                            messageToast = "You have been logged in successfully."

                            val accessToken = response.body()?.accessToken.toString()
                            val emailAddress = response.body()?.emailAddress.toString()
                            val fullNames = response.body()?.fullNames.toString()
                            val userId = response.body()?.userId.toString()

                            editor.putString("accessToken", accessToken)
                            editor.putString("emailAddress", emailAddress)
                            editor.putString("fullNames", fullNames)
                            editor.putString("userId", userId)
                            editor.putBoolean("isLoggedIn", true)
                            editor.apply()

                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)


                            CoroutineScope(Dispatchers.Main).launch {
                                customDialogToast.CustomDialogToast(
                                    context as Activity,
                                    messageToast
                                )
                            }

                        } else {

                            Log.e("-*-*-*- ", response.toString())
                            val code = response.code()
                            messageToast = if (code != 500){
                                val objectError = JSONObject(response.errorBody()!!.string())
                                val error = objectError.getString("details")
                                error
                            }else{
                                "There is something wrong. Please try again"
                            }

                            CoroutineScope(Dispatchers.IO).launch {

                                CoroutineScope(Dispatchers.Main).launch {
                                    customDialogToast.CustomDialogToast(
                                        context as Activity,
                                        messageToast
                                    )
                                }


                            }


                        }


                    }

                    override fun onFailure(call: Call<SuccessLogin>, t: Throwable) {
                        Log.e("-*-*error ", t.localizedMessage)
                        isLoggedIn = false
                        messageToast = "There is something wrong. Please try again"
                        CoroutineScope(Dispatchers.Main).launch {
                            customDialogToast.CustomDialogToast(
                                context as Activity,
                                messageToast
                            )
                        }

                        progressDialog.dismiss()
                    }
                })



            }.join()

        }

    }

    fun addPatient(context: Context, registerPatient: PatientRegistrationData){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                registerPatient(context, registerPatient)

            }.join()
        }

    }
    private suspend fun registerPatient(context: Context, registerPatient: PatientRegistrationData) {

        val preferences = context.getSharedPreferences(
            context.resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = preferences.edit()

        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Adding Patient in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            var messageToast = ""
            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                val formatter = Formatter()
                val stringMap = formatter.getHeaders(context)

                val baseUrl = context.getString(UrlData.BASE_URL.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
                val apiInterface = apiService.addPatient(stringMap, registerPatient)
                apiInterface.enqueue(object : Callback<PatientRegistrationData> {
                    override fun onResponse(
                        call: Call<PatientRegistrationData>,
                        response: Response<PatientRegistrationData>
                    ) {

                        CoroutineScope(Dispatchers.Main).launch { progressDialog.dismiss() }

                        if (response.isSuccessful) {
                            messageToast = "You have added the patient successfully."

                            val id = response.body()?.id.toString()
                            val firstName = response.body()?.firstName.toString()
                            val lastName = response.body()?.lastName.toString()
                            val patientNumber = response.body()?.patientId.toString()

                            val fullNames = "$firstName $lastName"

                            editor.putString("patientId", id)
                            editor.putString("patientFullNames", fullNames)
                            editor.putString("patientNumber", patientNumber)
                            editor.apply()

                            val intent = Intent(context, MainActivity::class.java)
                            intent.putExtra("details", "vitals")
                            context.startActivity(intent)

                            CoroutineScope(Dispatchers.Main).launch {
                                customDialogToast.CustomDialogToast(
                                    context as Activity,
                                    messageToast
                                )
                            }

                        } else {

                            Log.e("-*-*-* ", response.errorBody().toString())
                            val code = response.code()
                            messageToast = if (code != 500){
                                val objectError = JSONObject(response.errorBody()!!.string())
                                val error = objectError.getString("error")
                                error
                            }else{
                                "There is something wrong. Please try again"
                            }

                            CoroutineScope(Dispatchers.IO).launch {

                                CoroutineScope(Dispatchers.Main).launch {
                                    customDialogToast.CustomDialogToast(
                                        context as Activity,
                                        messageToast
                                    )
                                }


                            }


                        }


                    }

                    override fun onFailure(call: Call<PatientRegistrationData>, t: Throwable) {
                        Log.e("-*-*error ", t.localizedMessage)
                        messageToast = "There is something wrong. Please try again"
                        CoroutineScope(Dispatchers.Main).launch {
                            customDialogToast.CustomDialogToast(
                                context as Activity,
                                messageToast
                            )
                        }

                        progressDialog.dismiss()
                    }
                })



            }.join()

        }

    }


    fun addPatientVitals(context: Context, registerPatient: PatientsVitalsData){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                registerPatientVitals(context, registerPatient)

            }.join()
        }

    }
    private suspend fun registerPatientVitals(context: Context, registerPatient: PatientsVitalsData) {

        val preferences = context.getSharedPreferences(
            context.resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = preferences.edit()

        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Adding Patient vitals in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            var messageToast = ""
            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                val formatter = Formatter()
                val stringMap = formatter.getHeaders(context)

                val baseUrl = context.getString(UrlData.BASE_URL.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
                val apiInterface = apiService.addPatientVitals(stringMap, registerPatient)
                apiInterface.enqueue(object : Callback<PatientsVitalsData> {
                    override fun onResponse(
                        call: Call<PatientsVitalsData>,
                        response: Response<PatientsVitalsData>
                    ) {

                        CoroutineScope(Dispatchers.Main).launch { progressDialog.dismiss() }

                        if (response.isSuccessful) {
                            messageToast = "You have added the patient's vitals successfully."

                            val bmiValue = response.body()?.bmiValue

                            editor.putString("patientBmiValue", bmiValue.toString())
                            editor.apply()

                            val intent = Intent(context, MainActivity::class.java)

                            if (bmiValue?.toDouble()!! < 25){
                                intent.putExtra("details", "formA")
                            }else{
                                intent.putExtra("details", "formB")
                            }

                            context.startActivity(intent)

                            CoroutineScope(Dispatchers.Main).launch {
                                customDialogToast.CustomDialogToast(
                                    context as Activity,
                                    messageToast
                                )
                            }

                        } else {

                            Log.e("-*-*-* ", response.errorBody().toString())
                            val code = response.code()
                            messageToast = if (code != 500){
                                val objectError = JSONObject(response.errorBody()!!.string())
                                val error = objectError.getString("error")
                                error
                            }else{
                                "There is something wrong. Please try again"
                            }

                            CoroutineScope(Dispatchers.IO).launch {

                                CoroutineScope(Dispatchers.Main).launch {
                                    customDialogToast.CustomDialogToast(
                                        context as Activity,
                                        messageToast
                                    )
                                }


                            }


                        }


                    }

                    override fun onFailure(call: Call<PatientsVitalsData>, t: Throwable) {
                        Log.e("-*-*error ", t.localizedMessage)
                        messageToast = "There is something wrong. Please try again"
                        CoroutineScope(Dispatchers.Main).launch {
                            customDialogToast.CustomDialogToast(
                                context as Activity,
                                messageToast
                            )
                        }

                        progressDialog.dismiss()
                    }
                })



            }.join()

        }

    }



    fun addPatientForm(context: Context, registerPatient: PatientsFormData){

        CoroutineScope(Dispatchers.Main).launch {

            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                registerPatientForm(context, registerPatient)

            }.join()
        }

    }
    private suspend fun registerPatientForm(context: Context, registerPatient: PatientsFormData) {

        val job1 = Job()
        CoroutineScope(Dispatchers.Main + job1).launch {

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Please wait..")
            progressDialog.setMessage("Adding Patient form in progress..")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            var messageToast = ""
            val job = Job()
            CoroutineScope(Dispatchers.IO + job).launch {

                val formatter = Formatter()
                val stringMap = formatter.getHeaders(context)

                val baseUrl = context.getString(UrlData.BASE_URL.message)
                val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
                val apiInterface = apiService.addPatientForm(stringMap, registerPatient)
                apiInterface.enqueue(object : Callback<SuccessMessage> {
                    override fun onResponse(
                        call: Call<SuccessMessage>,
                        response: Response<SuccessMessage>
                    ) {

                        CoroutineScope(Dispatchers.Main).launch { progressDialog.dismiss() }

                        if (response.isSuccessful) {
                            messageToast = "You have added the patient's vitals successfully."

                            val details = response.body()?.details.toString()

                            val intent = Intent(context, PatientsListing::class.java)
                            context.startActivity(intent)

                            CoroutineScope(Dispatchers.Main).launch {
                                customDialogToast.CustomDialogToast(
                                    context as Activity,
                                    details
                                )
                            }

                        } else {

                            val code = response.code()
                            messageToast = if (code != 500){
                                val objectError = JSONObject(response.errorBody()!!.string())
                                val error = objectError.getString("error")
                                error
                            }else{
                                "There is something wrong. Please try again"
                            }

                            CoroutineScope(Dispatchers.IO).launch {

                                CoroutineScope(Dispatchers.Main).launch {
                                    customDialogToast.CustomDialogToast(
                                        context as Activity,
                                        messageToast
                                    )
                                }


                            }


                        }


                    }

                    override fun onFailure(call: Call<SuccessMessage>, t: Throwable) {
                        Log.e("-*-*error ", t.localizedMessage)
                        messageToast = "There is something wrong. Please try again"
                        CoroutineScope(Dispatchers.Main).launch {
                            customDialogToast.CustomDialogToast(
                                context as Activity,
                                messageToast
                            )
                        }

                        progressDialog.dismiss()
                    }
                })



            }.join()

        }

    }

    fun getPatientList(context: Context, date: String) = runBlocking{
        getAllPatientListing(context,date)
    }
    private suspend fun getAllPatientListing(context: Context, date: String): List<PatientListingData> {

        var patientListingDataList: List<PatientListingData> = ArrayList()

        var stringStringMap = Formatter().getHeaders(context)

        val job = Job()
        CoroutineScope(Dispatchers.IO + job).launch {

            val baseUrl = context.getString(UrlData.BASE_URL.message)
            val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)

            if (date != ""){
                val callSync: Call<PatientsList> = apiService.getDatedPatientListing(stringStringMap, date)
                try {
                    val response: Response<PatientsList> = callSync.execute()
                    val results = response.body()
                    if (results != null) {

                        val details = results.details
                        patientListingDataList = details

                    }

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }else{
                val callSync: Call<PatientsList> = apiService.getAllPatientListing(stringStringMap)
                try {
                    val response: Response<PatientsList> = callSync.execute()
                    val results = response.body()
                    if (results != null) {

                        val details = results.details
                        patientListingDataList = details

                    }

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }



        }.join()

        return patientListingDataList

    }


}

