package com.example.baytna.View

import com.example.baytna.Model.CategoryItems
import com.example.baytna.Model.WorkerItemsHome

interface HomeView {
    fun showCategories(categories: List<CategoryItems>)
    fun showRecommendedWorkers(workers: List<WorkerItemsHome>)
    fun showEmptyCategories()
    fun showEmptyWorkers()
}