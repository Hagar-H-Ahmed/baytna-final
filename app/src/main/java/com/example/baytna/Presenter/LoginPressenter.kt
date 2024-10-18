package com.example.baytna.Presenter

import com.example.baytna.Model.UserModel
import com.example.baytna.Model.UserModel.AuthCallback
import com.example.baytna.View.LoginView
import com.google.firebase.auth.FirebaseAuth

class LoginPresenter(private val view: LoginView, private val userModel: UserModel) {


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String?, password: String?) {

        if (!isValidEmail(email)) {
            view.onLoginFailure("Invalid email format.")
            return
        }
        if (password != null) {
            if (password.length < 6) {
                view.onLoginFailure("Password must be at least 6 characters long.")
                return
            }
        }

        userModel.authenticateUser(email, password, object : AuthCallback {
            override fun onSuccess() {
                view.onLoginSuccess()
            }

            override fun onFailure(message: String?) {
                view.onLoginFailure(message ?: "Login failed.")
            }
        })

    }
    private fun isValidEmail(email: String?): Boolean {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    }
}
