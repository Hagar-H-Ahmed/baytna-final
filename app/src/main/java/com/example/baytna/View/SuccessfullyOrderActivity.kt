package com.example.baytna.View

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.baytna.R

class SuccessfullyOrderActivity : AppCompatActivity() {


    private lateinit var done_btn :Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successfully_order)

        done_btn = findViewById(R.id.done_id)
        done_btn.setOnClickListener{
           val   intent = Intent(this , Home::class.java)
            startActivity(intent)}


        }


    }
