package com.stori.challenge.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.model.Resource
import com.stori.challenge.domain.usecase.GetMovementsUseCase
import com.stori.challenge.domain.usecase.GetProfileUseCase
import com.stori.challenge.presentation.ui.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase,
    getMovementsUseCase: GetMovementsUseCase
): ViewModel() {

    private var _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = _state

    init {
        val profile = getProfileUseCase()
        _state.update {
            it.copy(fullName = profile.fullName, photo = profile.photo)
        }
        viewModelScope.launch {

            getMovementsUseCase().collect { response ->
                if (response is Resource.Success) {
                    _state.update { it.copy(movements = response.data ?: listOf()) }
                }
            }
        }
    }

}
