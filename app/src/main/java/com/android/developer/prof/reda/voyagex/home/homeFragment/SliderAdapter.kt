package com.android.developer.prof.reda.voyagex.home.homeFragment

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.developer.prof.reda.voyagex.databinding.BannerViewHolderBinding
import com.bumptech.glide.Glide

class SliderAdapter: ListAdapter<SliderModel, SliderAdapter.SliderViewHolder> (SliderDiffCallback) {

    inner class SliderViewHolder(private val binding: BannerViewHolderBinding): ViewHolder(binding.root){
        fun bind(slider: SliderModel){
            Glide.with(itemView)
                .load(slider.url)
                .into(binding.bannerImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(BannerViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val slider = getItem(position)
        holder.bind(slider)
    }
}