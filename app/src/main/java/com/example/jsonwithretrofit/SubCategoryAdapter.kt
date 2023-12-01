package com.example.jsonwithretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonwithretrofit.databinding.CustomLayoutBinding
import com.squareup.picasso.Picasso

class SubCategoryAdapter(private val subCategoryList: MutableList<SubCategory>) :
    RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CustomLayoutBinding.inflate(inflater, parent, false)
        return SubCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        val subCategory = subCategoryList[position]


        // Load image using Picasso or any other image loading library
        Picasso.get().load(subCategory.banner_image).into(holder.binding.appImageView)
        holder.binding.appNameTextView.text = subCategory.name
        // Bind the data to your views in the ViewHolder
        holder.bind(subCategory)
    }

    override fun getItemCount(): Int = subCategoryList.size


    inner class SubCategoryViewHolder(val binding: CustomLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Initialize your views from itemView

        fun bind(subCategory: SubCategory) {
            // Bind subcategory data to your views
        }
    }
}
