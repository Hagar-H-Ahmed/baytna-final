package com.example.baytna.Presenter

import android.util.Log
import com.example.baytna.Model.CategoryItems
import com.example.baytna.Model.CategoryModel
import com.example.baytna.Const.WorkerCallback
import com.example.baytna.Const.WorkerCatCallback
import com.example.baytna.Const.CategoryCallback

import com.example.baytna.Model.WorkerItemsHome
import com.example.baytna.Model.WorkerModel
import com.example.baytna.View.HomeView
import com.example.baytna.View.WorkerListView


interface presenterWorkerList {
    fun loadWorkerList(categoryId: String)
}
class WokerListPresenter(private val workerModel: WorkerModel ,    private val view: WorkerListView
) : presenterWorkerList{



    override fun loadWorkerList(categoryName: String) {
        workerModel.getWorkersByCategoryName(categoryName, object : WorkerCallback {
            override fun onProfileLoaded(
                name: String?,
                price: String?,
                rate: Double?,
                imagePath: String?,
                categoryId: String?,
                mobile: String?
            ) {
            }

            override fun onAllWorkersLoaded(workers: List<WorkerItemsHome>) {
                if (workers.isNotEmpty()) {
                    view.showWorkersByCategory(workers)

                } else {
                    view.showEmptyWorkers()
                }
            }

            override fun onFailure(message: String) {
                view.showError(message)
            }
        })
    }
}
