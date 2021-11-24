package com.organisation.healthapp.retrofit

import com.organisation.healthapp.helperclass.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface Interface {

    @POST("/api/v1/auth/registration")
    fun registerUser(@Body userRegister: RegisterRequest): Call<RegisterResponse>

    @POST("/api/v1/auth/login")
    fun loginUser(@Body user: UserLogin): Call<SuccessLogin>

    @POST("/api/v1/patient/register-patient")
    fun addPatient(@HeaderMap headers: Map<String, String>, @Body patientRegistrationData: PatientRegistrationData): Call<PatientRegistrationData>

    @POST("/api/v1/patient/add-vitals")
    fun addPatientVitals(@HeaderMap headers: Map<String, String>, @Body patientRegistrationData: PatientsVitalsData): Call<PatientsVitalsData>

    @POST("/api/v1/patient/add-forms")
    fun addPatientForm(@HeaderMap headers: Map<String, String>, @Body patientRegistrationData: PatientsFormData): Call<SuccessMessage>

    @GET("/api/v1/patient/get-all-patient-listing/")
    fun getAllPatientListing(@HeaderMap headers: Map<String, String>): Call<PatientsList>

    @GET("/api/v1/patient/get-patient-listing/{visitationDate}")
    fun getDatedPatientListing(@HeaderMap headers: Map<String, String>, @Path("visitationDate") visitationDate: String): Call<PatientsList>


}