package com.example.baytna.Presenter

import com.example.baytna.Model.UserModel
import com.example.baytna.View.ProfileView

class ProfilePresenter(private val view: ProfileView, private val model: UserModel) {


    fun loadUserProfile() {
        model.getUserProfile(object : UserModel.ProfileCallback {
            override fun onProfileLoaded(
                name: String?,
                email: String?,
                mobile: String?,
                address: String?
            ) {
                view.displayUserProfile(name, email, mobile, address)
            }

            override fun onFailure(message: String) {
                view.showError(message)
            }
        })
    }
}