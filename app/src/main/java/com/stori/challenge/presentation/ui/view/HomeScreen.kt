package com.stori.challenge.presentation.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stori.challenge.presentation.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Home")
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.movements) {
        state.movements.forEach {
            Log.e("ASD", "${it.description}")
        }
    }
}