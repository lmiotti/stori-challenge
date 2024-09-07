package com.stori.challenge.presentation.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.presentation.ui.intent.RegistrationFormIntent
import com.stori.challenge.presentation.ui.state.RegistrationFormState
import com.stori.challenge.presentation.ui.viewmodel.RegistrationFormViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegistrationFormScreen(
    viewModel: RegistrationFormViewModel = hiltViewModel(),
    goToPhotoScreen: (RegistrationForm) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.goToPhotoScreen.collectLatest {
                goToPhotoScreen(it)
            }
        }
    }

    RegistrationFormScreenContent(
        handleIntent = { viewModel.handleIntent(it) },
        viewModel.state
    )
}

@Composable
fun RegistrationFormScreenContent(
    handleIntent: (RegistrationFormIntent) -> Unit,
    stateFlow: StateFlow<RegistrationFormState>
) {
    val state by stateFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(state) {
        val text = when {
            state.isNameEmptyError -> "Name empty"
            state.isSurnameEmptyError -> "Surname empty"
            state.isEmailEmptyError -> "Email Empty"
            state.isEmailFormatError -> "Email Format"
            state.isPasswordEmptyError -> "Password Empty"
            state.isPasswordFormatError -> "Password format"
            state.isConfirmPasswordEmptyError -> "Confirm password empty"
            state.isConfirmPasswordFormatError -> "Confirm password format"
            else -> ""
        }
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    var name by rememberSaveable { mutableStateOf("") }
    var surname by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(value = name, onValueChange = { name = it })
        TextField(value = surname, onValueChange = { surname = it })
        TextField(value = email, onValueChange = { email = it })
        TextField(value = password, onValueChange = { password = it })
        TextField(value = confirmPassword, onValueChange = { confirmPassword = it })

        Button(
            onClick = {
                handleIntent(RegistrationFormIntent.OnNextClicked(name, surname, email, password, confirmPassword))
            }
        ) {
            Text("Next")
        }
    }
}