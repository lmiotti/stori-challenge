package com.stori.challenge.presentation.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.domain.model.Resource
import com.stori.challenge.domain.usecase.RegisterUseCase
import com.stori.challenge.presentation.ui.intent.RegistrationPhotoIntent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@AssistedFactory
interface MyViewModelFactory {
    fun create(form: RegistrationForm): RegistrationPhotoViewModel
}

@HiltViewModel(assistedFactory = RegistrationPhotoViewModel.Factory::class)
class RegistrationPhotoViewModel @AssistedInject constructor(
    @Assisted private val form: RegistrationForm,
    private val registerUseCase: RegisterUseCase
): ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(form: RegistrationForm): RegistrationPhotoViewModel
    }

    private var _goToHomeScreen = MutableSharedFlow<Unit>()
    val goToHomeScreen: SharedFlow<Unit>
        get() = _goToHomeScreen

    fun handleIntent(intent: RegistrationPhotoIntent) {
        if (intent is RegistrationPhotoIntent.OnRegisterClicked) register(intent.photo)
    }

    private fun register(photo: Uri) {
        viewModelScope.launch {
            registerUseCase(form.copy(photo = photo)).collect {
                if (it is Resource.Success) _goToHomeScreen.emit(Unit)
            }
        }
    }
}