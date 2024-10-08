package com.android.developer.prof.reda.voyagex.home.homeFragment

import android.os.Parcelable
import com.android.developer.prof.reda.voyagex.util.GenericDiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class SliderModel(
    val url: String
): Parcelable{
    constructor(): this("")
}

val SliderDiffCallback = GenericDiffUtil<SliderModel>(
    areItemsSame = {oldItem, newItem -> oldItem.url == newItem.url },
    areContentSame = {oldItem, newItem -> oldItem == newItem }
)