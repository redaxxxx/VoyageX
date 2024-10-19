package com.android.developer.prof.reda.voyagex.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.developer.prof.reda.voyagex.R
import com.android.developer.prof.reda.voyagex.databinding.CategoryViewHolderBinding
import com.android.developer.prof.reda.voyagex.models.CategoryModel
import com.bumptech.glide.Glide

class CategoryAdapter: ListAdapter<CategoryModel, CategoryAdapter.CategoryViewHolder>(
    CategoryDiffCallback
) {
    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class CategoryViewHolder(private val binding: CategoryViewHolderBinding): ViewHolder(binding.root){
        fun bind(category: CategoryModel, position: Int){
            Glide.with(itemView)
                .load(category.ImagePath)
                .into(binding.catPic)

            binding.catTitle.text = category.Name

            binding.root.setOnClickListener {
                lastSelectedPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(selectedPosition)
            }

            binding.catTitle.setTextColor(itemView.context.getColor(R.color.white))

            if (selectedPosition == position){
                binding.catPic.setBackgroundResource(0)
                binding.mainLayoutCat.setBackgroundResource(R.drawable.cat_blue_bg)
                binding.catTitle.visibility = View.VISIBLE
            }else{
                binding.catPic.setBackgroundResource(R.drawable.category_bg)
                binding.mainLayoutCat.setBackgroundResource(0)
                binding.catTitle.visibility = View.GONE
            }
        }
    }

    companion object CategoryDiffCallback: DiffUtil.ItemCallback<CategoryModel>(){
        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem == newItem
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
        holder.bind(category, position)
    }
}