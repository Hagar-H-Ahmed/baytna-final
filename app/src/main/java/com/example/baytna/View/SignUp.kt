package com.example.baytna.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.baytna.Model.UserModel
import com.example.baytna.Presenter.LoginPresenter
import com.example.baytna.Presenter.SignUpPresenter
import com.example.baytna.R

class SignUp : AppCompatActivity(),SignUpView {
    override fun onCreate(savedInstanceState: Bundle?) {


        lateinit var presenter: SignUpPresenter
        lateinit var backToLogin: ImageButton
        lateinit var signUpBtn: Button
        lateinit var edittxtName: EditText
        lateinit var edittxtEmail: EditText
        lateinit var edittxtMobile: EditText
        lateinit var edittxtPass: EditText
        lateinit var edittxtConfirmPass: EditText

        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_sign_up)


                presenter = SignUpPresenter(this, UserModel())

        val rootView: View = findViewById(R.id.main)
        setupUI(rootView)
        backToLogin = findViewById(R.id.backtoLogin_id)
        signUpBtn = findViewById(R.id.sign_btn2_id)

        edittxtName = findViewById(R.id.fname_id)
        edittxtEmail = findViewById(R.id.signupemail_id)
        edittxtPass = findViewById(R.id.passsign_id)
        edittxtConfirmPass = findViewById(R.id.confirmpass_id)
        edittxtMobile = findViewById(R.id.mob_id)

        setupUI(findViewById(R.id.main))


        backToLogin.setOnClickListener{
            var intent_back_login = Intent(this, Login::class.java)
            startActivity(intent_back_login)
        }

        signUpBtn.setOnClickListener{
            val email: String = edittxtEmail.getText().toString()
            val password: String = edittxtPass.getText().toString()
            val confirmPassword: String = edittxtConfirmPass.getText().toString()
            val name: String = edittxtName.getText().toString()
            val mobile: String = edittxtMobile.getText().toString()
            presenter.signUp(name , mobile , email, password, confirmPassword);



        }





    }

    override fun onSignUpSuccess() {
        Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();

        var intent_signup_btn2 = Intent(this, Login::class.java)
        startActivity(intent_signup_btn2)
    }

    override fun onSignUpFailure(message: String?) {
        Toast.makeText(this, "Sign Up Failed:  " + message, Toast.LENGTH_SHORT).show();

    }

    private fun setupUI(view: View) {
        // Set up touch listener for non-text box views to hide the keyboard
        if (view !is EditText && view !is Button && view !is ImageButton) {
            view.setOnTouchListener { v, event ->
                hideKeyboard(v)
                true
            }
        }

        // If a layout contains children, iterate through them and apply the touch listener
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }


    // Method to hide the keyboard
    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

