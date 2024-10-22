package com.android.developer.prof.reda.voyagex.mvvm

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.developer.prof.reda.voyagex.util.HOME_ACTIVITY
import com.android.developer.prof.reda.voyagex.util.INTRODUCTION_KEY
import com.android.developer.prof.reda.voyagex.util.LOGIN_FRAGMENT
import com.android.developer.prof.reda.voyagex.util.Resources
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class IntroViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val sharedPreferences: SharedPreferences
): ViewModel() {
    private val _navigate = MutableStateFlow(0)
    val navigate: StateFlow<Int>
        get() = _navigate

    private val currentUser = firebaseAuth.currentUser
    private val isButtonClicked = sharedPreferences.getBoolean(INTRODUCTION_KEY, false)

    init {
        if (currentUser != null){
           viewModelScope.launch {
               _navigate.emit(HOME_ACTIVITY)
           }
        }else if (isButtonClicked){
            viewModelScope.launch {
                _navigate.emit(LOGIN_FRAGMENT)
            }
        }else Unit
    }

    fun startClickButton(){
        sharedPreferences.edit().putBoolean(INTRODUCTION_KEY, true).apply()
    }
}