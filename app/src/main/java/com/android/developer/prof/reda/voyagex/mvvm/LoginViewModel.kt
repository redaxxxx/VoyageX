package com.android.developer.prof.reda.voyagex.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.developer.prof.reda.voyagex.util.LoginFailedState
import com.android.developer.prof.reda.voyagex.util.LoginValidation
import com.android.developer.prof.reda.voyagex.util.Resources
import com.android.developer.prof.reda.voyagex.util.validateLoginEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel(){

    private val _login = MutableStateFlow<Resources<FirebaseUser>>(Resources.Unspecified())
    val login: StateFlow<Resources<FirebaseUser>>
        get() = _login

    private val _validation = Channel<LoginFailedState>()
    val validation = _validation.receiveAsFlow()

    fun loginUser(email: String, password: String){
        if (checkValidation(email, password)){
            runBlocking {
                _login.emit(Resources.Loading())
            }

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    viewModelScope.launch {
                        _login.emit(Resources.Success(it.user))
                    }
                }.addOnFailureListener {
                    viewModelScope.launch {
                        _login.emit(Resources.Failed(it.message))
                    }
                }
        }else{
            val loginFailedState = LoginFailedState(
                validateLoginEmail(email),
            )

            runBlocking {
                _validation.send(loginFailedState)
            }
        }
    }

    private fun checkValidation(email: String, password: String): Boolean{
        val emailValidation = validateLoginEmail(email)

        return emailValidation is LoginValidation.Success
    }
}