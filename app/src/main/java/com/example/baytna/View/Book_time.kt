package com.example.baytna.View
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.baytna.R
import com.example.baytna.databinding.ActivityBookTimeBinding
import com.google.android.material.button.MaterialButtonToggleGroup

class Book_time : AppCompatActivity() {


    private lateinit var binding: ActivityBookTimeBinding
    private lateinit var dropMenuCity: AutoCompleteTextView
    private lateinit var selectDate: AutoCompleteTextView
    private lateinit var selectTime: AutoCompleteTextView
    private lateinit var categoryNameBook : TextView
    private lateinit var workerName : TextView
    private lateinit var Price : TextView
    private lateinit var back_btn :ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_time)

        binding = ActivityBookTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryNameBook=findViewById(R.id.under_app_bar_text)
        workerName=findViewById(R.id.workerNameID)
        Price=findViewById(R.id.price_value)

        binding.bSubmit.isEnabled = false

        back_btn = findViewById(R.id.backIcon1)
        setUpButtonToggleListeners()

        binding.bSubmit.setOnClickListener {
            validateAndSubmit()
        }

        val serviceName = intent.getStringExtra("categoryname")
        val servicePrice = intent.getStringExtra("price")
        val showworkername =intent.getStringExtra("workername")

        // Set the data to the TextViews

        categoryNameBook.text = serviceName
        Price.text = servicePrice
        workerName.text = showworkername




        back_btn.setOnClickListener {
        val intentt = Intent(this, Home::class.java)
        startActivity(intentt)}

    }

    private fun setUpButtonToggleListeners() {
        val toggleGroupDay: MaterialButtonToggleGroup = binding.bSelectDay
        val toggleGroupWorkTime: MaterialButtonToggleGroup = binding.bWorkTime

        val buttonToggleListener = MaterialButtonToggleGroup.OnButtonCheckedListener { _, _, _ ->
            validateInputs()
        }

        toggleGroupDay.addOnButtonCheckedListener(buttonToggleListener)
        toggleGroupWorkTime.addOnButtonCheckedListener(buttonToggleListener)

        binding.whereTil.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateInputs()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }


    private fun validateInputs() {
        val isDaySelected = binding.bSelectDay.checkedButtonId != View.NO_ID
        val isWorkTimeSelected = binding.bWorkTime.checkedButtonId != View.NO_ID
        val isAddressEntered = !binding.whereTil.editText?.text.isNullOrEmpty()

        binding.bSubmit.isEnabled = isDaySelected && isWorkTimeSelected && isAddressEntered
    }


    private fun validateAndSubmit() {
        val isDaySelected = binding.bSelectDay.checkedButtonId != View.NO_ID
        val isWorkTimeSelected = binding.bWorkTime.checkedButtonId != View.NO_ID
        val isAddressEntered = !binding.whereTil.editText?.text.isNullOrEmpty()


        if (!isDaySelected) {
            Toast.makeText(this, "You must choose a day", Toast.LENGTH_SHORT).show()
            return
        }


        if (!isWorkTimeSelected) {
            Toast.makeText(this, "You must choose work time", Toast.LENGTH_SHORT).show()
            return
        }


        if (!isAddressEntered) {
            binding.whereTil.error = "Address is required"
            return
        } else {
            binding.whereTil.error = null
            val intent = Intent(this, SuccessfullyOrderActivity::class.java)
            startActivity(intent)
        }


        Toast.makeText(this, "Submission Successful", Toast.LENGTH_SHORT).show()


    }
}
