package com.android.developer.prof.reda.voyagex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.developer.prof.reda.voyagex.databinding.BannerViewHolderBinding
import com.android.developer.prof.reda.voyagex.models.SliderModel
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

    companion object SliderDiffCallback: DiffUtil.ItemCallback<SliderModel>(){
        override fun areItemsTheSame(oldItem: SliderModel, newItem: SliderModel): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: SliderModel, newItem: SliderModel): Boolean {
            return oldItem == newItem
        }
    }
    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val slider = getItem(position)
        holder.bind(slider)
    }
}