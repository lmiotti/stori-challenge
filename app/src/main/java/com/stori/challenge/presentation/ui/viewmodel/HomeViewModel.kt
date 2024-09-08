package com.stori.challenge.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.model.Movement
import com.stori.challenge.domain.model.Profile
import com.stori.challenge.domain.model.Resource
import com.stori.challenge.domain.usecase.GetMovementsUseCase
import com.stori.challenge.domain.usecase.GetProfileUseCase
import com.stori.challenge.domain.usecase.SignOutUseCase
import com.stori.challenge.presentation.ui.intent.HomeIntent
import com.stori.challenge.presentation.ui.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    getMovementsUseCase: GetMovementsUseCase,
    private val logOutUseCase: SignOutUseCase
): BaseViewModel<HomeState, HomeIntent>(
    initialState = HomeState()
) {

    private var _goToLoginScreen = MutableSharedFlow<Unit>()
    val goToLoginScreen: SharedFlow<Unit>
        get() = _goToLoginScreen

    private var _showError = MutableSharedFlow<String>()
    val showError: SharedFlow<String>
        get() = _showError

    init {
        viewModelScope.launch {
            combine(getProfileUseCase(), getMovementsUseCase()) { prf, mvm ->
                Pair(prf, mvm)
            }.collect { (profile, movements) ->
                val isLoading = profile is Resource.Loading || movements is Resource.Loading

                when {
                    profile is Resource.Success ->
                        _state.update { it.copy(name = profile.data?.name ?: "", isLoading = isLoading) }
                    movements is Resource.Success -> {
                        val movementsList = movements.data ?: listOf()
                        val balance = movementsList.mapNotNull { it.amount }.sum()
                        _state.update {
                            it.copy(movements = movementsList , isLoading = isLoading, balance = balance)
                        }
                    }
                    profile is Resource.Failure || movements is Resource.Failure -> {
                        _state.update { it.copy(isLoading = isLoading) }
                        _showError.emit(profile.error ?: movements.error ?: "")
                    }
                    else -> _state.update { it.copy(isLoading = isLoading) }
                }
            }
        }
    }

    override fun handleIntent(intent: HomeIntent) {
        if (intent is HomeIntent.OnSignOutClicked) signOut()
    }

    private fun signOut() {
        logOutUseCase()
        viewModelScope.launch {
            _goToLoginScreen.emit(Unit)
        }
    }
}
