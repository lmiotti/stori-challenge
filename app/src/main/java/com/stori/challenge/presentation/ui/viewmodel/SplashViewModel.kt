package com.stori.challenge.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.usecase.IsUserLoggedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    isUserLoggedUseCase: IsUserLoggedUseCase
): ViewModel() {

    private var _isUserLogged = MutableSharedFlow<Boolean>()
    val isUserLogged: SharedFlow<Boolean>
        get() = _isUserLogged

    init {
        viewModelScope.launch {
            val result = isUserLoggedUseCase()
            _isUserLogged.emit(result)
        }
    }
}