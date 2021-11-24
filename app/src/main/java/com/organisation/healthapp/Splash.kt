package com.organisation.healthapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.organisation.healthapp.auth.Login

class Splash : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val preferences = getSharedPreferences(
            resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({


            //Check if there's data in shared preference, if not Register user otherwise go to main menu

            val isLoggedIn = preferences.getBoolean("isLoggedIn", false)
            if (isLoggedIn){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }



            finish()
        }, 3000)

    }
}