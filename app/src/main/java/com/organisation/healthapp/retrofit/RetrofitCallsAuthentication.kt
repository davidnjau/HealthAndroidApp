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
import com.organisation.healthapp.helperclass.UrlData
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

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


}

