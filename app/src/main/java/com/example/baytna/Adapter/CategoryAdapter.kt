package com.example.baytna.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.baytna.Model.CategoryItems
import com.example.baytna.R
import com.example.baytna.View.WorkerList

class CategoryAdapter(private val categoryList: List<CategoryItems> , private val context: Context) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTitle: TextView = itemView.findViewById(R.id.TextCategory)
        val categoryImage: ImageView = itemView.findViewById(R.id.CategoryPic)
        val cardView: CardView = itemView.findViewById(R.id.Card_Cat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryTitle.text = category.Name

        // Load image using Glide

        holder.cardView.setOnClickListener {
            val intent = Intent(context,WorkerList::class.java)
            intent.putExtra("categoryName", category.Name)

            context.startActivity(intent)
        }


        Glide.with(holder.itemView.context)
            .load(category.ImagePath)
            .apply(RequestOptions().error(R.drawable.mob))
            .into(holder.categoryImage)

        // Set background color
        try {
            val color = Color.parseColor(category.Background)
            holder.cardView.setCardBackgroundColor(color)
        } catch (e: IllegalArgumentException) {
            holder.cardView.setCardBackgroundColor(Color.WHITE)
        }

    }
}
