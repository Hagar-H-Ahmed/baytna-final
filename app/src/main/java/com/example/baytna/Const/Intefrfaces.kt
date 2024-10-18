package com.example.baytna.Const

import com.example.baytna.Model.WorkerItemsHome

interface WorkerCallback {
    fun onProfileLoaded(
        name: String?,
        price: String?,
        rate: Double?,
        imagePath: String?,
        categoryId: String?,
        mobile: String?
    )



    fun onAllWorkersLoaded(workers: List<WorkerItemsHome>)
    fun onFailure(message: String)
}

interface WorkerCatCallback{

    fun onAllWorkersLoaded(workers: List<WorkerItemsHome>)
    fun onFailure(message: String)
}

interface CategoryCallback {
    fun onCategoriesLoaded()
    fun onFailure(message: String)
}