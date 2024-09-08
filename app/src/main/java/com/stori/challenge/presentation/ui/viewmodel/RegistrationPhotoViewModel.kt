package com.stori.challenge.presentation.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.di.DefaultDispatcher
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.domain.model.Resource
import com.stori.challenge.domain.usecase.RegisterUseCase
import com.stori.challenge.presentation.ui.intent.RegistrationPhotoIntent
import com.stori.challenge.presentation.ui.state.RegistrationFormState
import com.stori.challenge.presentation.ui.state.RegistrationPhotoState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AssistedFactory
interface MyViewModelFactory {
    fun create(form: RegistrationForm): RegistrationPhotoViewModel
}

@HiltViewModel(assistedFactory = RegistrationPhotoViewModel.Factory::class)
class RegistrationPhotoViewModel @AssistedInject constructor(
    @Assisted private val form: RegistrationForm,
    private val registerUseCase: RegisterUseCase,
    @DefaultDispatcher val defaultDispatcher: CoroutineDispatcher
): ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(form: RegistrationForm): RegistrationPhotoViewModel
    }

    private var _goToHomeScreen = MutableSharedFlow<Unit>()
    val goToHomeScreen: SharedFlow<Unit>
        get() = _goToHomeScreen

    private var _showError = MutableSharedFlow<String>()
    val showError: SharedFlow<String>
        get() = _showError

    private val _state = MutableStateFlow(RegistrationPhotoState())
    val state: StateFlow<RegistrationPhotoState>
        get() = _state


    fun handleIntent(intent: RegistrationPhotoIntent) {
        when(intent) {
            is RegistrationPhotoIntent.OnTakePictureClicked -> _state.update { it.copy(photo = intent.photo) }
            is RegistrationPhotoIntent.OnRegisterClicked -> register()
            else -> {}
        }
    }

    private fun register() {
        viewModelScope.launch {
            registerUseCase(form.copy(photo = _state.value.photo)).collect { response ->
                when(response) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> showSuccessDialog()
                    is Resource.Failure -> {
                        response.error?.let { _showError.emit(it) }
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }

    private suspend fun showSuccessDialog() {
        _state.update { it.copy(showSuccess = true, isLoading = false) }
        withContext(defaultDispatcher) { delay(5000L) }
        _goToHomeScreen.emit(Unit)
    }
}