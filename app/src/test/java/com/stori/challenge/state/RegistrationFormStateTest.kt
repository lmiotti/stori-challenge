package com.stori.challenge.state

import com.stori.challenge.presentation.ui.state.RegistrationFormState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegistrationFormStateTest {

    @Test
    fun whenEmailIsEmptyErrorIsFalse() {
        // Given
        val email = ""
        val state = RegistrationFormState(email = email)

        // Assert
        assert(!state.isEmailError)
    }


    @Test
    fun whenEmailIsNotEmptyAndEmailIsValidErrorIsFalse() {
        // Given
        val email = "test@test.com"
        val state = RegistrationFormState(email = email)

        // Assert
        assert(!state.isEmailError)
    }

    @Test
    fun whenPasswordIsEmptyErrorIsFalse() {
        // Given
        val password = ""
        val state = RegistrationFormState(password = password)

        // Assert
        assert(!state.isPasswordError)
    }

    @Test
    fun whenPasswordIsNotEmptyAndPasswordIsValidErrorIsFalse() {
        // Given
        val password = "Pass123!"
        val state = RegistrationFormState(password = password)

        // Assert
        assert(!state.isPasswordError)
    }

    @Test
    fun whenConfirmPasswordIsDifferentFromPasswordErrorIsTrue() {
        // Given
        val password = "Pass123!"
        val confirmPassword = "Pass1234!"
        val state = RegistrationFormState(
            password = password,
            confirmPassword = confirmPassword
        )

        // Assert
        assert(state.isConfirmPasswordError)
    }

    @Test
    fun whenConfirmPasswordIsEquakFromPasswordErrorIsFalse() {
        // Given
        val password = "Pass123!"
        val confirmPassword = "Pass123!"
        val state = RegistrationFormState(
            password = password,
            confirmPassword = confirmPassword
        )

        // Assert
        assert(!state.isPasswordError)
    }

    @Test
    fun whenFormIsValidThenButtonIsEnabled() {
        // Given
        val name = "Test"
        val surname = "Test"
        val email = "test@test.com"
        val password = "Pass123!"
        val confirmPassword = "Pass123!"
        val state = RegistrationFormState(
            name = name,
            surname = surname,
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )

        // Assert
        assert(state.isNextButtonEnabled)
    }
}
