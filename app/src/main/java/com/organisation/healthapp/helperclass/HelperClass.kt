package com.organisation.healthapp.helperclass

import java.util.*


data class Results(
    val statusCode: Int,
    val details: Any
)
data class ErrorMessage(
    val error: String
)
data class SuccessMessage(
    val details: String

)

data class SuccessLogin(

    val accessToken:String,
    val userId:String,
    val fullNames:String,
    val emailAddress:String,
    val roles:List<String>,
)
data class UserLogin(
    val username:String,
    val password:String
    )

data class RegisterResponse(

    val userId:String,
    val fullNames:String,
    val emailAddress:String,
    val roles:List<String>,
)
data class RegisterRequest(

    val password:String,
    val fullNames:String,
    val emailAddress:String,
    val confirmPassword:String,
)
data class PatientRegistrationData(
    val patientId:String,
    val registrationDate:Date,
    val firstName:String,
    val lastName:String,
    val dateOfBirth:Date,
    val gender:String
)
data class PatientsVitalsData(
    val patientUUID:String,
    val visitDate:Date,
    val weightInKgs:Double,
    val heightInCm:Double,
    val bmiValue: Double?
)
data class PatientsFormData(
    val patientUUID:String,
    val visitDate:Date,
    val generalHealth:String,

    val weightDiet:String?,
    val takingDrugs:String?,

    val comments: String
)
data class PatientListingData(
    val userName : String,
    val age: Int,
    val bmiValue: Double
)
data class PatientsList(
    val size: Int,
    val next: String?,
    val previous: String?,
    val details: List<PatientListingData>
)
