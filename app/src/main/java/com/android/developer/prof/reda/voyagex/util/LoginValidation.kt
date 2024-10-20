package com.android.developer.prof.reda.voyagex.util

sealed class LoginValidation {
    object Success: LoginValidation()
    data class Failed(val message: String): LoginValidation()
}

data class LoginFailedState(
    val email: LoginValidation,
    val password: LoginValidation
)