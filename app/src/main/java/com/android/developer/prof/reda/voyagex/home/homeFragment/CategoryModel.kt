package com.android.developer.prof.reda.voyagex.home.homeFragment

import android.os.Parcelable
import com.android.developer.prof.reda.voyagex.util.GenericDiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(
    val Id: Int,
    val ImagePath:  String,
    val Name: String
): Parcelable{
    constructor(): this(0, "", "")
}

val CategoryDiffCallback = GenericDiffUtil<CategoryModel>(
    areItemsSame = {oldItem, newItem -> oldItem.Id == newItem.Id },
    areContentSame = {oldItem, newItem -> oldItem == newItem }
)