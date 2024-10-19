package com.android.developer.prof.reda.voyagex.models

import android.os.Parcelable
import com.android.developer.prof.reda.voyagex.util.GenericDiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationModel(
    val id: Int,
    val loc: String
): Parcelable{
    constructor(): this(0,"")
}