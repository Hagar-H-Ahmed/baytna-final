package com.example.baytna.View

import HomePresenter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baytna.Adapter.CategoryAdapter
import com.example.baytna.Model.CategoryItems
import com.example.baytna.Model.CategoryModel
import com.example.baytna.Model.WorkerItemsHome
import com.example.baytna.Model.WorkerModel
import com.example.baytna.R

class gridViewCategory : AppCompatActivity(), HomeView {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var backArrow: ImageView
    private lateinit var presenter: HomePresenter
    private var categoryList: List<CategoryItems> = emptyList()
    private lateinit var categoryName: TextView
    private lateinit var cardLayout: View




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_view_category)

        // Initialize the models and presenter
        val categoryModel = CategoryModel()
        presenter = HomePresenter(this, workerModel = WorkerModel(), categoryModel = categoryModel)

        // Setup RecyclerView
        recyclerView = findViewById(R.id.Category_RecycleView_grid)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Load categories dynamically using the presenter
        presenter.loadCategories()



        // Setup back arrow to navigate back to Home
        backArrow = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }

    // HomeView Implementation

    override fun showCategories(categories: List<CategoryItems>) {
        // Update the category list dynamically


        categoryList = categories
        categoryAdapter = CategoryAdapter(categoryList,this)
        recyclerView.adapter = categoryAdapter
    }


    override fun showEmptyCategories() {
        // Handle empty categories
        recyclerView.visibility = View.GONE
    }

    override fun showRecommendedWorkers(workers: List<WorkerItemsHome>) {
    }

    override fun showEmptyWorkers() {
    }}