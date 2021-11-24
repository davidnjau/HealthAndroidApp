package com.organisation.healthapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.organisation.healthapp.R
import com.organisation.healthapp.helperclass.CustomDialogToast
import com.organisation.healthapp.helperclass.RegisterRequest
import com.organisation.healthapp.helperclass.UserLogin
import com.organisation.healthapp.retrofit.RetrofitCallsAuthentication
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btnSignIn
import kotlinx.android.synthetic.main.activity_login.etEmailAddress
import kotlinx.android.synthetic.main.activity_login.etPassword

class Login : AppCompatActivity() {


    var customDialogToast = CustomDialogToast()
    private var retrofitCallsAuthentication: RetrofitCallsAuthentication = RetrofitCallsAuthentication()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvRegister.setOnClickListener {

            val intent = Intent(this, Register::class.java)
            startActivity(intent)

        }

        btnSignIn.setOnClickListener {

            var messageToast = ""
            val emailAddress = etEmailAddress.text.toString()
            val passWord = etPassword.text.toString()

            if (!TextUtils.isEmpty(emailAddress)
                && !TextUtils.isEmpty(passWord)){

                val userLogin = UserLogin(
                    emailAddress, passWord)

                retrofitCallsAuthentication.loginUser(this, userLogin)

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