package com.example.baytna.View
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.baytna.Model.UserModel
import com.example.baytna.Presenter.ProfilePresenter
import com.example.baytna.R



class Profile : AppCompatActivity() , ProfileView {
    private lateinit var mobileEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var editMobileButton: ImageButton
    private lateinit var editAddressButton: ImageButton
    private lateinit var logoutButton: Button
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var backToHome :ImageButton



    private lateinit var presenter: ProfilePresenter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        // Toolbar setup
        val toolbar: Toolbar = findViewById(R.id.profile_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        // Initialize views
        mobileEditText = findViewById(R.id.mobile_edit)
        addressEditText = findViewById(R.id.address_edit)
        editMobileButton = findViewById(R.id.edit_mobile_btn)
        editAddressButton = findViewById(R.id.edit_address_btn)
        logoutButton = findViewById(R.id.logout_btn)
        nameTextView= findViewById(R.id.user_name)
        emailTextView = findViewById(R.id.email_text)

        presenter = ProfilePresenter(this, UserModel())

        presenter.loadUserProfile()



        // Edit mobile number logic
        editMobileButton.setOnClickListener {
            if (mobileEditText.isEnabled) {
                val updatedMobile = mobileEditText.text.toString()
                Toast.makeText(this, "Mobile number updated: $updatedMobile", Toast.LENGTH_SHORT).show()
                mobileEditText.isEnabled = false
            } else {
                mobileEditText.isEnabled = true
                mobileEditText.requestFocus()
            }
        }

        // Edit address logic
        editAddressButton.setOnClickListener {
            if (addressEditText.isEnabled) {
                val updatedAddress = addressEditText.text.toString()
                // Show a confirmation message
                Toast.makeText(this, "Address updated: $updatedAddress", Toast.LENGTH_SHORT).show()
                addressEditText.isEnabled = false

            } else {
                // Enable editing
                addressEditText.isEnabled = true
                addressEditText.requestFocus()

                // Show the keyboard
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE)  as InputMethodManager
                imm.showSoftInput(addressEditText, InputMethodManager.SHOW_IMPLICIT)


            }
        }

        // Logout button logic with confirmation dialog
        logoutButton.setOnClickListener {
            showLogoutDialog()
        }
    }

    override fun displayUserProfile(name: String?, email: String?, mobile: String?, address: String?) {
        // Display user data on the UI
        name?.let {
            nameTextView.text = it
        }
        email?.let {
            emailTextView.text = it
        }

        mobile?.let {
            mobileEditText.setText(it)
        }
        address?.let {
            addressEditText.setText(it)
        } ?: run {
            addressEditText.setHint("No address provided")
        }
    }


        override fun showError(message: String?) {
            Toast.makeText(this, message ?: "An error occurred", Toast.LENGTH_SHORT).show()
        }

        override fun showSuccessMessage(message: String) {
            Toast.makeText(this, "Success: $message", Toast.LENGTH_SHORT).show()
        }


        private fun showLogoutDialog() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Logout")
            builder.setMessage("Are you sure you want to logout?")

            builder.setPositiveButton("Logout") { dialogInterface: DialogInterface, _: Int ->
                // Perform logout action here
                Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }

            builder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }

            builder.show()
        }

        override fun onSupportNavigateUp(): Boolean {
            onBackPressed()
            return true
        }

    }
