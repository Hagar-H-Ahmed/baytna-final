package com.example.baytna.Model

import android.content.Context
import com.example.baytna.Const.capitalizeEachWord
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserModel() {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().getReference("Users")




    fun authenticateUser(email: String?, password: String?, callback: AuthCallback) {
        auth.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    callback.onSuccess()
                } else {
                    callback.onFailure(task.exception?.message ?: "Login Failed")
                }
            }
    }
    fun registerUser(name: String? , mobile: String? , email: String?, password: String?, callback: AuthCallback) {

        // make name formated befor stored on firbase then call it in sign up presenter

        val formattedName = capitalizeEachWord(name)
        auth.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid

                    // Create a user map with additional data
                    val userMap = mapOf(
                        "name" to formattedName,
                        "mobile" to mobile,
                        "email" to email

                        // address
                    )
                    // Save user data in Firestore
                    uid?.let {
                        database.child(uid).setValue(userMap)
                            .addOnSuccessListener {
                                callback.onSuccess()

                            }
                            .addOnFailureListener { e ->
                                callback.onFailure(e.message)
                            }
                    }                } else {
                    callback.onFailure(task.exception?.message ?: "Registration Failed")
                }
            }
    }

    fun getUserProfile(callback: ProfileCallback) {
        val uid = auth.currentUser?.uid
        uid?.let {
            database.child(it).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Extract data from snapshot
                    val name = snapshot.child("name").getValue(String::class.java)
                    val email = snapshot.child("email").getValue(String::class.java)
                    val mobile = snapshot.child("mobile").getValue(String::class.java)
                    val address = snapshot.child("address").getValue(String::class.java)


                    callback.onProfileLoaded(name, email, mobile, address)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback.onFailure(error.message)
                }
            })
        }
    }



    interface ProfileCallback {
        fun onProfileLoaded(name: String?, email: String?, mobile: String?, address: String?)
        fun onFailure(message: String)
    }
    interface AuthCallback {
        fun onSuccess()
        fun onFailure(message: String?)
    }
}
