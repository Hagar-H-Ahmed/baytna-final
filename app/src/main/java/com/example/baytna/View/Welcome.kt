package com.example.baytna.View

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.baytna.R

class Welcome : AppCompatActivity() {
   lateinit var loginw_btn :Button
    lateinit var signupw_btn :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        loginw_btn = findViewById(R.id.welcom_btn1)
        signupw_btn = findViewById(R.id.welcom_btn2)



        // Set click listener for the login button
        loginw_btn.setOnClickListener {
            val intentLogin = Intent(this, Login::class.java)
            startActivity(intentLogin)
        }

        // Set click listener for the signup button
        signupw_btn.setOnClickListener {
            val intentSignup = Intent(this, SignUp::class.java)
            startActivity(intentSignup)
        }
    }
}