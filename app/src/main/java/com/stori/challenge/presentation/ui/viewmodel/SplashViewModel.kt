package com.stori.challenge.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.di.DefaultDispatcher
import com.stori.challenge.domain.usecase.IsUserLoggedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    isUserLoggedUseCase: IsUserLoggedUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
): ViewModel() {

    private var _isUserLogged = MutableSharedFlow<Boolean>()
    val isUserLogged: SharedFlow<Boolean>
        get() = _isUserLogged

    init {
        viewModelScope.launch {
            val result = isUserLoggedUseCase()
            withContext(defaultDispatcher) { delay(DELAY_TIME) }
            _isUserLogged.emit(result)
        }
    }

    companion object {
        private const val DELAY_TIME = 2000L
    }
}