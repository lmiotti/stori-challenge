package com.stori.challenge.viewmodel

import app.cash.turbine.test
import com.stori.challenge.presentation.ui.intent.RegistrationFormIntent
import com.stori.challenge.presentation.ui.state.RegistrationFormState
import com.stori.challenge.presentation.ui.viewmodel.RegistrationFormViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegistrationFormViewModelTest {

    @InjectMocks
    private lateinit var viewModel: RegistrationFormViewModel

    @Test
    fun whenOnNameChanged_thenNameIsUpdated() {
        runTest {
            // Given
            val name = "Test"

            // When
            viewModel.handleIntent(RegistrationFormIntent.OnNameChanged(name))

            // Then
            viewModel.state.test {
                Assert.assertEquals(RegistrationFormState(name = name), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun whenOnSurnameChanged_thenSurnameIsUpdated() {
        runTest {
            // Given
            val surname = "Test"

            // When
            viewModel.handleIntent(RegistrationFormIntent.OnSurameChanged(surname))

            // Then
            viewModel.state.test {
                Assert.assertEquals(RegistrationFormState(surname = surname), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
    @Test
    fun whenOnEmailChanged_thenEmailIsUpdated() {
        runTest {
            // Given
            val email = "test@test.com"

            // When
            viewModel.handleIntent(RegistrationFormIntent.OnEmailChanged(email))

            // Then
            viewModel.state.test {
                Assert.assertEquals(RegistrationFormState(email = email), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun whenOnPasswordChanged_thenPasswordIsUpdated() {
        runTest {
            // Given
            val password = "password"

            // When
            viewModel.handleIntent(RegistrationFormIntent.OnPasswordChanged(password))

            // Then
            viewModel.state.test {
                Assert.assertEquals(RegistrationFormState(password = password), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
    @Test
    fun whenOnConfirmPasswordChanged_thenConfirmPasswordIsUpdated() {
        runTest {
            // Given
            val password = "password"

            // When
            viewModel.handleIntent(RegistrationFormIntent.OnConfirmPasswordChanged(password))

            // Then
            viewModel.state.test {
                Assert.assertEquals(RegistrationFormState(confirmPassword = password), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
