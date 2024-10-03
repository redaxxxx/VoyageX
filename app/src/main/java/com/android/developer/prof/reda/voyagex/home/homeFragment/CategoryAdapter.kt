package com.android.developer.prof.reda.voyagex.home.homeFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.developer.prof.reda.voyagex.databinding.CategoryViewHolderBinding
import com.android.developer.prof.reda.voyagex.util.GenericDiffUtil
import com.bumptech.glide.Glide

class CategoryAdapter: ListAdapter<CategoryModel, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback) {

    inner class CategoryViewHolder(private val binding: CategoryViewHolderBinding): ViewHolder(binding.root){
        fun bind(category: CategoryModel){
            Glide.with(itemView)
                .load(category.ImagePath)
                .into(binding.catPic)

            binding.catTitle.text = category.Name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }
}