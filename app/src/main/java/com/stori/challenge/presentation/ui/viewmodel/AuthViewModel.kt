package com.stori.challenge.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
): ViewModel() {

    fun onLoginClicked(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase(email, password).collect {
                // TODO: Handle flow
            }
        }
    }
}