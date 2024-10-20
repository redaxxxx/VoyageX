package com.android.developer.prof.reda.voyagex.util

sealed class RegisterValidation {
    object Success: RegisterValidation()
    data class Failed(val message: String): RegisterValidation()
}

data class RegisterFailedState(
    val username: RegisterValidation,
    val email: RegisterValidation,
    val password: RegisterValidation
)