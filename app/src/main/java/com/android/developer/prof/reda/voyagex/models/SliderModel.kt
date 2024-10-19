package com.android.developer.prof.reda.voyagex.models

import android.os.Parcelable
import com.android.developer.prof.reda.voyagex.util.GenericDiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class SliderModel(
    val url: String
): Parcelable{
    constructor(): this("")
}