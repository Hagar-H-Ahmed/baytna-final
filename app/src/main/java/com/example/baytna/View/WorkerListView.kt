package com.example.baytna.View

import com.example.baytna.Model.WorkerItemsHome


interface WorkerListView {

    fun showWorkersByCategory(workers: List<WorkerItemsHome>)
    fun showEmptyWorkers()
    fun showError(message: String)
    fun showLoading(isLoading: Boolean)

}