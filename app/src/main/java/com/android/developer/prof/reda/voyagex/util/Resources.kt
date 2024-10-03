package com.android.developer.prof.reda.voyagex.util

sealed class Resources<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T>(): Resources<T>()
    class Success<T>(data: T?): Resources<T>(data)
    class Failed<T>(message: String?): Resources<T>(message= message)
    class Unspecified<T>(): Resources<T>()
}