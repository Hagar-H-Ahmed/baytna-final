package com.example.baytna.Model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class CategoryModel {

    private val database = FirebaseDatabase.getInstance().getReference("Category")

    // Fetch all categories
    fun getAllCategories(callback: CategoryCallback) {
        val allCategories = mutableListOf<CategoryItems>()
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {

                        val category = childSnapshot.getValue(CategoryItems::class.java)

                        category?.let { allCategories.add(it) }

                    }
                    callback.onAllCategoriesLoaded(allCategories)
                } else {
                    callback.onAllCategoriesLoaded(emptyList())  // Handle empty category list
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback.onFailure(error.message)
            }
        })
    }
}

interface CategoryCallback {
    fun onAllCategoriesLoaded(categories: List<CategoryItems>)
    fun onFailure(message: String)
}
