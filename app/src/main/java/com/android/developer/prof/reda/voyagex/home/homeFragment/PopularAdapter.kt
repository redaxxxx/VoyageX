package com.android.developer.prof.reda.voyagex.home.homeFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.developer.prof.reda.voyagex.databinding.PopularViewHolderBinding
import com.bumptech.glide.Glide

class PopularAdapter: ListAdapter<PopularModel, PopularAdapter.PopularViewHolder>(PopularDiffCallback) {
    inner class PopularViewHolder(private val binding: PopularViewHolderBinding): ViewHolder(binding.root){
        fun bind(popularItem: PopularModel){
            Glide.with(itemView)
                .load(popularItem.pic)
                .into(binding.picPopular)

            binding.placeTitle.text = popularItem.title
            binding.placeAddressTv.text = popularItem.address
            binding.priceTv.text = "$${popularItem.price}"
            binding.ratingTv.text = "${popularItem.score}"
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
}