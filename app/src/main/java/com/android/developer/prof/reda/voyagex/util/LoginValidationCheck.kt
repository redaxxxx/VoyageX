package com.android.developer.prof.reda.voyagex.util

import android.util.Patterns

fun validateLoginEmail(email: String): LoginValidation{

    if(email.isEmpty()){
        return LoginValidation.Failed("Email Cannot be empty")
    }

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        return LoginValidation.Failed("Wrong email format")
    }
    return LoginValidation.Success
}

fun validateLoginPassword(password: String): LoginValidation{
    val passwordPattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")

    if (password.isEmpty()){
        return LoginValidation.Failed("Password cannot be empty")
    }
    if (!password.matches(passwordPattern)){
        return LoginValidation.Failed("Password must be at least 8 characters long and contain both letters and numbers")
    }

    return LoginValidation.Success
}