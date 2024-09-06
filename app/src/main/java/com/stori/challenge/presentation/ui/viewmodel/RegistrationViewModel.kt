package com.stori.challenge.presentation.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.usecase.RegisterUseCase
import com.stori.challenge.presentation.ui.intent.RegistrationIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    fun handleIntent(intent: RegistrationIntent) {
        if (intent is RegistrationIntent.OnRegisterClicked) {
            register(intent.email, intent.surname, intent.email, intent.password, intent.photo)
        }
    }

    private fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        photo: Uri
    ) {
        viewModelScope.launch {
            registerUseCase(name, surname, email, password, photo).collect {
                Log.e("ASD", "$it")
            }
        }
    }
}