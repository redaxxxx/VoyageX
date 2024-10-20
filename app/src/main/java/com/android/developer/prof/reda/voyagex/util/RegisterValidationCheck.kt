package com.android.developer.prof.reda.voyagex.util

import android.util.Patterns

fun validateRegisterUsername(username: String): RegisterValidation{
    val usernamePattern = Regex("^[A-Za-z][A-Za-z0-9._]{2,14}$")

    if (username.isEmpty()){
        return RegisterValidation.Failed("Username cannot is empty")
    }

    if (!usernamePattern.matches(username)){
        return RegisterValidation.Failed("Invalid username. Use 3-15 characters with letters, numbers, underscores, or periods.")
    }

    return RegisterValidation.Success
}
fun validateRegisterEmail(email: String): RegisterValidation{

    if(email.isEmpty()){
        return RegisterValidation.Failed("Email Cannot be empty")
    }

    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        return RegisterValidation.Failed("Wrong email format")
    }
    return RegisterValidation.Success
}

fun validateRegisterPassword(password: String): RegisterValidation{
    val passwordPattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")

    if (password.isEmpty()){
        return RegisterValidation.Failed("Password cannot be empty")
    }
    if (!password.matches(passwordPattern)){
        return RegisterValidation.Failed("Password must be at least 8 characters long and contain both letters and numbers")
    }

    return RegisterValidation.Success
}