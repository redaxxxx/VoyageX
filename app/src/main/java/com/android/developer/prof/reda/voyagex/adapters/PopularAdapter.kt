package com.android.developer.prof.reda.voyagex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.developer.prof.reda.voyagex.databinding.PopularViewHolderBinding
import com.android.developer.prof.reda.voyagex.models.ItemModel
import com.bumptech.glide.Glide

class PopularAdapter: ListAdapter<ItemModel, PopularAdapter.PopularViewHolder>(
    PopularDiffCallback
) {
    inner class PopularViewHolder(private val binding: PopularViewHolderBinding): ViewHolder(binding.root){
        fun bind(popularItem: ItemModel){
            Glide.with(itemView)
                .load(popularItem.pic)
                .into(binding.picPopular)

            binding.placeTitle.text = popularItem.title
            binding.placeAddressTv.text = popularItem.address
            binding.priceTv.text = "$${popularItem.price}"
            binding.ratingTv.text = "${popularItem.score}"

            itemView.setOnClickListener {
                onClickItem?.invoke(popularItem)
            }
        }
    }

    companion object PopularDiffCallback: DiffUtil.ItemCallback<ItemModel>(){
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val popular = getItem(position)
        holder.bind(popular)
    }

    var onClickItem: ((ItemModel) -> Unit)? = null
}