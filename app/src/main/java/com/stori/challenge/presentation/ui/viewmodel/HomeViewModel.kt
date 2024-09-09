package com.stori.challenge.presentation.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.stori.challenge.di.MainDispatcher
import com.stori.challenge.domain.model.Movement
import com.stori.challenge.domain.model.Profile
import com.stori.challenge.domain.model.Resource
import com.stori.challenge.domain.usecase.GetMovementsUseCase
import com.stori.challenge.domain.usecase.GetProfileUseCase
import com.stori.challenge.domain.usecase.SignOutUseCase
import com.stori.challenge.presentation.ui.intent.HomeIntent
import com.stori.challenge.presentation.ui.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    getMovementsUseCase: GetMovementsUseCase,
    private val logOutUseCase: SignOutUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
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
        viewModelScope.launch(dispatcher) {
            merge(getProfileUseCase(), getMovementsUseCase())
                .onCompletion {
                    _state.update { it.copy(isLoading = false) }
                }.collect { response ->
                    when(response) {
                        is Resource.Loading ->
                            _state.update { it.copy(isLoading = true) }
                        is Resource.Success ->
                            handleSuccessResponse(response)
                        is Resource.Failure ->
                            _showError.emit(response.error ?: "")
                    }
                }
        }
    }

    private fun handleSuccessResponse(response: Resource.Success<out Any>) {
        val data = response.data
        if (data is Profile) {
            _state.update { it.copy(name = data.name) }
        }
        if (data is List<*>) {
            val movementList = data.filterIsInstance<Movement>()
            val balance = movementList.map { it.amount }.sum()
            _state.update {
                it.copy(movements = movementList, balance = balance)
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
