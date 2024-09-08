package com.stori.challenge.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.model.Resource
import com.stori.challenge.domain.usecase.GetMovementsUseCase
import com.stori.challenge.domain.usecase.GetProfileUseCase
import com.stori.challenge.domain.usecase.SignOutUseCase
import com.stori.challenge.presentation.ui.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sign

@HiltViewModel
class HomeViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    getMovementsUseCase: GetMovementsUseCase,
    private val logOutUseCase: SignOutUseCase
): ViewModel() {

    private var _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = _state

    private var _goToLoginScreen = MutableSharedFlow<Unit>()
    val goToLoginScreen: SharedFlow<Unit>
        get() = _goToLoginScreen

    init {
        viewModelScope.launch {
            getProfileUseCase().collect {
                when(it) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> _state.update { it.copy(name = it.name, photo = it.photo) }
                    is Resource.Failure -> {}
                }
            }
            getMovementsUseCase().collect {
                when(it) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> _state.update { it.copy(movements = it.movements) }
                    is Resource.Failure -> {}
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun handleIntent() {
        signOut()
    }

    private fun signOut() {
        logOutUseCase()
        viewModelScope.launch {
            _goToLoginScreen.emit(Unit)
        }
    }
}
