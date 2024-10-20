package com.android.developer.prof.reda.voyagex.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: Long,
    val username: String,
    val email: String,
):Parcelable{
    constructor(): this(0, "", "")
}
