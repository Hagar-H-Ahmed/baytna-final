package com.example.baytna.View

import HomePresenter
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baytna.Adapter.CategoryAdapter
import com.example.baytna.Adapter.WorkerAdapter
import com.example.baytna.Const.capitalizeEachWord
import com.example.baytna.Model.CategoryItems
import com.example.baytna.Model.CategoryModel
import com.example.baytna.Model.WorkerItemsHome
import com.example.baytna.Model.WorkerModel
import com.example.baytna.R

class Home : AppCompatActivity(), HomeView {

    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recyclerViewWorker: RecyclerView
    private lateinit var workerAdapter: WorkerAdapter
    private lateinit var viewAll: TextView
    private lateinit var bookBtn :Button

    private lateinit var presenter: HomePresenter
    private lateinit var userName: TextView


    private var categoryList: List<CategoryItems> = emptyList()
    private var workerList: List<WorkerItemsHome> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        // Initialize Category RecyclerView
        recyclerViewCategory = findViewById(R.id.Category_RecycleView)
        recyclerViewCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        // Initialize Worker RecyclerView
        recyclerViewWorker = findViewById(R.id.workers_RecycleView)
        recyclerViewWorker.layoutManager = LinearLayoutManager(this)


        val workerModel = WorkerModel()
        val categoryModel = CategoryModel()
        presenter = HomePresenter(this, workerModel, categoryModel)

        userName = findViewById(R.id.userName_Id)
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userNameShared = sharedPreferences.getString("userName", "User")
        userName.text = capitalizeEachWord(userNameShared)


        // Load Categories and Workers using the presenter
        presenter.loadCategories()
        presenter.loadRecommendedWorkers()

        // Setup viewAll button click to navigate to gridViewCategory
        viewAll = findViewById(R.id.viewAllId)
        viewAll.setOnClickListener {
            val intent = Intent(this, gridViewCategory::class.java)
            startActivity(intent)
        }
        userName=findViewById(R.id.userName_Id)
        userName.setOnClickListener{
          val  intent = Intent(this,Profile::class.java)
            startActivity(intent)
        }





    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout?")

        builder.setPositiveButton("Logout") { dialogInterface: DialogInterface, _: Int ->
            // Perform logout action here
            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        builder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }

        builder.show()
    }

    override fun showCategories(categories: List<CategoryItems>) {
        categoryList = categories
        categoryAdapter = CategoryAdapter(categoryList,this)
        recyclerViewCategory.adapter = categoryAdapter
    }

    override fun showRecommendedWorkers(workers: List<WorkerItemsHome>) {
        workerList = workers
        workerAdapter = WorkerAdapter(workerList, categoryList,this)
        recyclerViewWorker.adapter = workerAdapter
    }

    override fun showEmptyCategories() {
        recyclerViewCategory.visibility = View.GONE
    }

    override fun showEmptyWorkers() {
        recyclerViewWorker.visibility = View.GONE
    }
}