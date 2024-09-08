package com.stori.challenge.presentation.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.stori.challenge.presentation.ui.component.StoriTopBar
import com.stori.challenge.presentation.ui.intent.HomeIntent
import com.stori.challenge.presentation.ui.viewmodel.HomeViewModel
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    goToLoginScreen: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.movements) {
        state.movements.forEach {
            Log.e("ASD", "${it.description}")
        }
    }

    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.goToLoginScreen.collectLatest {
                goToLoginScreen()
            }
        }
    }

    Scaffold(
        topBar = {
            StoriTopBar(
                showAction = true,
                onActionClicked = { viewModel.handleIntent(HomeIntent.OnSignOutClicked) }
            )
        }
    ) {
        HomeScreenContent(it)
    }
}

@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues
) {
}