package com.organisation.healthapp.auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.organisation.healthapp.R
import com.organisation.healthapp.helperclass.CustomDialogToast
import com.organisation.healthapp.helperclass.RegisterRequest
import com.organisation.healthapp.retrofit.RetrofitCallsAuthentication
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    var customDialogToast = CustomDialogToast()
    private var retrofitCallsAuthentication: RetrofitCallsAuthentication = RetrofitCallsAuthentication()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tvLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        btnSignIn.setOnClickListener {

            var messageToast = ""
            val emailAddress = etEmailAddress.text.toString()
            val fullNames = etFullName.text.toString()
            val passWord = etPassword.text.toString()
            val confirmPasword = etConfirmPassword.text.toString()

            if (!TextUtils.isEmpty(emailAddress)
                && !TextUtils.isEmpty(fullNames)
                && !TextUtils.isEmpty(passWord)
                && !TextUtils.isEmpty(confirmPasword) ){

                if (passWord != confirmPasword){

                    val userRegister = RegisterRequest(
                        passWord, fullNames, emailAddress, confirmPasword)

                    retrofitCallsAuthentication.registerUser(this, userRegister)


                }else{
                   messageToast = "Make sure your password match"
                    customDialogToast.CustomDialogToast(
                        this,
                        messageToast
                    )
                }

            }else{
                messageToast = "Make sure all field have values"
                customDialogToast.CustomDialogToast(
                    this,
                    messageToast
                )
            }



        }

    }
}