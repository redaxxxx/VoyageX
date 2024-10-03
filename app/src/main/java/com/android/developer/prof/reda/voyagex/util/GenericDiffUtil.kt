package com.android.developer.prof.reda.voyagex.util

import androidx.recyclerview.widget.DiffUtil

class GenericDiffUtil<T>(
    private val areItemsSame: (oldItem:T, newItem: T) -> Boolean,
    private val areContentSame: (oldItem: T, newItem: T) -> Boolean
):DiffUtil.ItemCallback<T>(){
    override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return areContentSame(oldItem, newItem)
    }

}