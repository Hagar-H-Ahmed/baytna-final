package com.example.baytna.Presenter

import android.util.Log
import com.example.baytna.Const.capitalizeEachWord
import com.example.baytna.Model.UserModel
import com.example.baytna.Model.UserModel.AuthCallback
import com.example.baytna.View.SignUpView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class SignUpPresenter(view: SignUpView, private val userModel: UserModel)  {
    private val view: SignUpView = view

    fun signUp(name: String? ,mobile: String? , email: String?, password: String, confirmPassword: String) {


        if (name.isNullOrEmpty()) {
            view.onSignUpFailure("Name is required.")
            return
        }
        // make name formated befor stored on firbase
        val formattedName = capitalizeEachWord(name)


        if (email.isNullOrEmpty()) {
            view.onSignUpFailure("Email is required.")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.onSignUpFailure("Enter a valid email.")
            return
        }

        if (mobile.isNullOrEmpty()) {
            view.onSignUpFailure("Mobile number is required.")
            return
        }

        if (mobile.length < 11) {
            view.onSignUpFailure("Enter a valid mobile number.")
            return
        }

        if (password.isNullOrEmpty()) {
            view.onSignUpFailure("Password is required.")
            return
        }

        if (password.length < 6) {
            view.onSignUpFailure("Password must be at least 6 characters.")
            return
        }

        if (confirmPassword.isNullOrEmpty()) {
            view.onSignUpFailure("Confirm password is required.")
            return
        }

        if (confirmPassword != password) {
            view.onSignUpFailure("Passwords do not match.")
            return
        }
        userModel.registerUser(formattedName, mobile, email, password, object : AuthCallback {
            override fun onSuccess() {
                view.onSignUpSuccess()
            }

            override fun onFailure(message: String?) {
                view.onSignUpFailure(message ?: "Registration Failed")
            }
        })
    }

    private fun isValidEmail(email: String?): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    }


}
