package com.android.developer.prof.reda.voyagex.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemModel(
    val address: String,
    val bed: Int,
    val dateTour: String,
    val description: String,
    val distance: String,
    val duration: String,
    val pic: String,
    val price: Int,
    val score: Double,
    val timeTour: String,
    val title: String,
    val tourGuideName: String,
    val tourGuidePhone: String,
    val tourGuidePic: String
): Parcelable{
    constructor(): this("",0,"","","","","",0,0.0,
        "","","","","")
}