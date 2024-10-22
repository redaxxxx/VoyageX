package com.android.developer.prof.reda.voyagex.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.developer.prof.reda.voyagex.models.User
import com.android.developer.prof.reda.voyagex.util.COUNTERS_DOC
import com.android.developer.prof.reda.voyagex.util.COUNTERS_REF
import com.android.developer.prof.reda.voyagex.util.RegisterFailedState
import com.android.developer.prof.reda.voyagex.util.RegisterValidation
import com.android.developer.prof.reda.voyagex.util.Resources
import com.android.developer.prof.reda.voyagex.util.validateRegisterEmail
import com.android.developer.prof.reda.voyagex.util.validateRegisterPassword
import com.android.developer.prof.reda.voyagex.util.validateRegisterUsername
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firesotre: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private val _register = MutableStateFlow<Resources<User>>(Resources.Unspecified())
    val register: StateFlow<Resources<User>>
        get() = _register

    private val _userID = MutableStateFlow<Resources<Long>>(Resources.Unspecified())
    val userID: StateFlow<Resources<Long>>
        get() = _userID

    private val _validation = Channel<RegisterFailedState>()
    val validation = _validation.receiveAsFlow()

    fun signupUser(user: User, password: String){

        if (checkValidation(user.username, user.email, password)){
            firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener {
                    val counterRef = firesotre.collection(COUNTERS_REF).document(COUNTERS_DOC)

                    firesotre.runTransaction {transaction ->
                        // Get current user id counter
                        val snapshot = transaction.get(counterRef)

                        // get current user ID or default 923568 if it's first user
                        var currentId = snapshot.getLong("current_id") ?: 923568

                        //increment
                        currentId += 1

                        //update counter to new counter
                        transaction.update(
                            counterRef,
                            "current_id",
                            currentId
                        )

                        //add new user
                        transaction.set(firesotre.collection("users").document(currentId.toString()),
                            user)

                        viewModelScope.launch {
                            _userID.emit(Resources.Success(currentId))
                        }
                        currentId
                    }.addOnFailureListener {
                        viewModelScope.launch {
                            _register.emit(Resources.Failed(it.message))
                        }
                    }.addOnSuccessListener {
                        viewModelScope.launch {
                            _register.emit(Resources.Success(user))
                        }
                    }
                }.addOnFailureListener {
                    viewModelScope.launch {
                        _register.emit(Resources.Failed(it.message))
                    }
                }
        }else{
            val registerFailedState = RegisterFailedState(
                validateRegisterUsername(user.username),
                validateRegisterEmail(user.email),
                validateRegisterPassword(password)
            )

            runBlocking {
                _validation.send(registerFailedState)
            }
        }


    }
    private fun checkValidation(username: String, email: String, password: String): Boolean{
        val validateUsername = validateRegisterUsername(username)
        val validateEmail = validateRegisterEmail(email)
        val validatePassword = validateRegisterPassword(password)

        return validateUsername is RegisterValidation.Success &&
                validateEmail is RegisterValidation.Success &&
                validatePassword is RegisterValidation.Success
    }
}