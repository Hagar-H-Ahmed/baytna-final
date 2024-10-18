package com.example.baytna.View


interface ProfileView {
    fun showError(message: String?)
    fun showSuccessMessage(message: String)
    fun displayUserProfile(name: String?, email: String?, mobile: String?, address: String?)
}