package com.android.developer.prof.reda.voyagex.home.homeFragment

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.developer.prof.reda.voyagex.databinding.RecommendedViewHolderBinding
import com.bumptech.glide.Glide

class RecommendAdapter: ListAdapter<RecommendModel, RecommendAdapter.RecommendViewHolder>(RecommendDiffCallback) {

    inner class RecommendViewHolder(private val binding: RecommendedViewHolderBinding): ViewHolder(binding.root){
        fun bind(recommend: RecommendModel){
            Glide.with(itemView)
                .load(recommend.pic)
                .into(binding.picRecommend)

            binding.placeTitle.text = recommend.title
            binding.rateTv.text = recommend.score.toString()
            binding.addressTv.text = recommend.address
            binding.price.text = "$${recommend.price}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        return RecommendViewHolder(RecommendedViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val recommend = getItem(position)
        holder.bind(recommend)
    }
}