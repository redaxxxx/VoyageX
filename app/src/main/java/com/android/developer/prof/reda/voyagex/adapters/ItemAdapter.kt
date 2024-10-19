package com.android.developer.prof.reda.voyagex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.developer.prof.reda.voyagex.databinding.RecommendedViewHolderBinding
import com.android.developer.prof.reda.voyagex.models.ItemModel
import com.bumptech.glide.Glide

class ItemAdapter: ListAdapter<ItemModel, ItemAdapter.RecommendViewHolder>(ItemDiffCallback) {

    inner class RecommendViewHolder(private val binding: RecommendedViewHolderBinding): ViewHolder(binding.root){
        fun bind(item: ItemModel){
            Glide.with(itemView)
                .load(item.pic)
                .into(binding.picRecommend)

            binding.placeTitle.text = item.title
            binding.rateTv.text = item.score.toString()
            binding.addressTv.text = item.address
            binding.price.text = "$${item.price}"

            itemView.setOnClickListener {
                onClickItem?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        return RecommendViewHolder(RecommendedViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    companion object ItemDiffCallback: DiffUtil.ItemCallback<ItemModel>(){
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem == newItem
        }

    }
    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val recommend = getItem(position)
        holder.bind(recommend)
    }

    var onClickItem: ((ItemModel) -> Unit)? = null
}