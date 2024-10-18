package com.example.baytna.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.baytna.Model.CategoryItems
import com.example.baytna.Model.WorkerItemsHome
import com.example.baytna.R
import com.example.baytna.View.Book_time


class WorkerListAdapter(private val WorkerList: List<WorkerItemsHome>,
                        private val categoryList: String? , private val context: Context) :
        RecyclerView.Adapter<WorkerListAdapter.MyViewHolder>() {

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val categoryName: TextView = itemView.findViewById(R.id.workercategoryName)
            val workerName: TextView = itemView.findViewById(R.id.workerName)
            val workerPhone: TextView = itemView.findViewById(R.id.WorkerPhone)
            val workerPrice: TextView = itemView.findViewById(R.id.price)
            val workerImg: ImageView = itemView.findViewById(R.id.workerImgId)
            val workerRate: RatingBar = itemView.findViewById(R.id.ratingBar)
            val cardView: CardView = itemView.findViewById(R.id.Card_Worker)
            val bookBtnHome: Button = itemView.findViewById(R.id.bookBtnHome)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.workerhome_item, parent, false)
            return MyViewHolder(itemView)
        }



        override fun getItemCount(): Int {
            return WorkerList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val worker = WorkerList[position]

            // Set worker details
            holder.workerName.text = worker.Name
            holder.workerPhone.text = worker.Mobile
            holder.workerPrice.text = worker.price
            holder.workerRate.rating = worker.Rate.toFloat()

            // Load worker image using Glide
            Glide.with(holder.itemView.context)
                .load(worker.ImagePath)
                .apply(RequestOptions().error(R.drawable.mob))
                .into(holder.workerImg)

            holder.bookBtnHome.setOnClickListener {
                val intent1 = Intent(context , Book_time::class.java)
                intent1.putExtra("workername" , holder.workerName.text)
                intent1.putExtra("categoryname" , holder.categoryName.text)
                intent1.putExtra("price" , holder.workerPrice.text)

                context.startActivity(intent1)


            }



            // Set the category name or a fallback message if not found
            holder.categoryName.text = "Category: ${categoryList?: "Unknown"}"

        }
    }



