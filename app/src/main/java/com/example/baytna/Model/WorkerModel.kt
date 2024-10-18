package com.example.baytna.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baytna.Const.WorkerCallback
import com.example.baytna.Const.WorkerCatCallback
import com.example.baytna.Const.CategoryCallback

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class WorkerModel {

    private val categoriesDatabase = FirebaseDatabase.getInstance().getReference("Category")
    private val database = FirebaseDatabase.getInstance().getReference("Workers")

    // Fetch a single worker by ID
    fun getWorker(id: String, callback: WorkerCallback) {
        val workerId = database.child(id)

        workerId.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("Name").getValue(String::class.java)
                val price = snapshot.child("Price").getValue(String::class.java)
                val rate = snapshot.child("Rate").getValue(Double::class.java)
                val imagePath = snapshot.child("ImagePath").getValue(String::class.java)
                val categoryId = snapshot.child("CategoryId").getValue(String::class.java)
                val mobile = snapshot.child("Mobile").getValue(String::class.java)

                callback.onProfileLoaded(name, price, rate, imagePath, categoryId, mobile)
            }

            override fun onCancelled(error: DatabaseError) {
                callback.onFailure(error.message)
            }
        })
    }

    // Fetch all workers with a rating >= 4.0 and take the top 8
    fun getAllWorkers(callback: WorkerCallback) {
        val allWorkers = mutableListOf<WorkerItemsHome>()
        val highRateThreshold = 4.0

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        val worker = childSnapshot.getValue(WorkerItemsHome::class.java)
                        worker?.let {
                            // Add workers who meet the high rating criteria
                            if (it.Rate != null && it.Rate >= highRateThreshold) {
                                allWorkers.add(it)
                            }
                        }
                    }

                    // Sort workers by rating in descending order and take the top 8
                    allWorkers.sortByDescending { it.Rate }
                    val topWorkers = allWorkers.take(8)

                    callback.onAllWorkersLoaded(topWorkers)
                } else {
                    callback.onAllWorkersLoaded(emptyList()) // Handle empty data
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback.onFailure(error.message)
            }
        })
    }

    // Load categories and populate the categoryNameToIdMap

    // Fetch workers by category name using categoryNameToIdMap

    fun getNameById(categoryName: String, callback: (String?) -> Unit) {
        categoriesDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        val categoryNameInDb = childSnapshot.child("Name").getValue(String::class.java)
                        val categoryId = childSnapshot.child("Id").getValue(Any::class.java) // Use Any to handle both Long and String

                        if (categoryNameInDb == categoryName && categoryId != null) {
                            val categoryIdStr = when (categoryId) {
                                is Long -> categoryId.toString() // Convert Long to String
                                is String -> categoryId // Already a String
                                else -> null // Handle unexpected types
                            }

                            callback(categoryIdStr)
                            return
                        }
                    }
                    callback(null) // Category not found
                } else {
                    callback(null) // No categories found
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseQuery", "Error fetching category: ${error.message}")
                callback(null)
            }
        })
    }





    fun getWorkersByCategoryName(categoryName: String, callback: WorkerCallback) {

        val categoriesRef = categoriesDatabase
        categoriesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(categorySnapshot: DataSnapshot) {
                if (categorySnapshot.exists()) {

                    var matchingCategoryId: Long? = null
                    // Find the CategoryId for the given categoryName
                    for (categoryChild in categorySnapshot.children) {
                        val category = categoryChild.getValue(CategoryItems::class.java)

                        if (category?.Name?.trim()?.equals(categoryName.trim(), ignoreCase = true) == true) {
                            matchingCategoryId = category.Id
                            break
                        }
                    }

                    if (matchingCategoryId != null) {
                        val allWorkers = mutableListOf<WorkerItemsHome>()
                        val highRateThreshold = 5.0


                        database.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(workerSnapshot: DataSnapshot) {
                                if (workerSnapshot.exists()) {
                                    for (workerChild in workerSnapshot.children) {
                                        val worker = workerChild.getValue(WorkerItemsHome::class.java)
                                        worker?.let {
                                            // Check if the worker's CategoryId matches the retrieved category's ID
                                            if (worker.CategoryId == matchingCategoryId && worker.Rate <= highRateThreshold) {
                                                allWorkers.add(it)
                                            }
                                        }
                                    }

                                    // Sort workers by rating in descending order and return the results
                                    allWorkers.sortByDescending { it.Rate }
                                    callback.onAllWorkersLoaded(allWorkers)
                                } else {
                                    callback.onAllWorkersLoaded(emptyList()) // Handle empty data
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                callback.onFailure(error.message)
                            }
                        })
                    } else {

                        callback.onAllWorkersLoaded(emptyList())
                    }
                } else {
                    callback.onAllWorkersLoaded(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback.onFailure(error.message)
            }
        })
    }}

