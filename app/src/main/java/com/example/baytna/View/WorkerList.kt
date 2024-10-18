package com.example.baytna.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baytna.Adapter.WorkerAdapter
import com.example.baytna.Adapter.WorkerListAdapter
import com.example.baytna.Model.WorkerItemsHome
import com.example.baytna.Model.WorkerModel
import com.example.baytna.Presenter.WokerListPresenter
import com.example.baytna.R

class WorkerList : AppCompatActivity() ,WorkerListView{

    private lateinit var categoryNameworker : TextView
    private lateinit var recyclerViewWorker: RecyclerView
    private lateinit var workerAdapter: WorkerListAdapter
    private lateinit var workerPresenter : WokerListPresenter
    private lateinit var backtohome_btn : ImageButton
    private var workerList: List<WorkerItemsHome> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_list)


        backtohome_btn=findViewById(R.id.back_workerbtn)

        backtohome_btn.setOnClickListener{
            var intent : Intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        workerPresenter = WokerListPresenter(WorkerModel() , this)
        categoryNameworker=findViewById(R.id.workersCat_id)


        recyclerViewWorker = findViewById(R.id.workersCat_RecycleView)
        recyclerViewWorker.layoutManager = LinearLayoutManager(this)

        val categoryName = intent.getStringExtra("categoryName")
        categoryNameworker.text=categoryName
        if (categoryName != null) {
            workerPresenter.loadWorkerList(categoryName)
        }
    }


    override fun showWorkersByCategory(workers: List<WorkerItemsHome>) {

        workerList = workers
        workerAdapter = WorkerListAdapter(workerList, intent?.extras?.getString("categoryName"), this )
       // Pass both workerList and categoryList
        recyclerViewWorker.adapter = workerAdapter


    }

    override fun showEmptyWorkers() {
    }

    override fun showError(message: String) {
    }

    override fun showLoading(isLoading: Boolean) {
    }
}