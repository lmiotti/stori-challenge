package com.stori.challenge.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<T, V>(initialState: T): ViewModel() {

    protected var _state = MutableStateFlow(initialState)
    val state: StateFlow<T>
        get() = _state

    abstract fun handleIntent(intent: V)
}