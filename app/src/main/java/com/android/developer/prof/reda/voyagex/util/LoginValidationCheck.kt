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