package com.stori.challenge.state

import com.stori.challenge.presentation.ui.state.LoginState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginStateTest {
    @Test
    fun whenEmailIsEmptyErrorIsFalse() {
        // Given
        val email = ""
        val state = LoginState(email = email)

        // Assert
        assert(!state.isEmailError)
    }

    @Test
    fun whenEmailIsNotEmptyAndEmailIsValidErrorIsFalse() {
        // Given
        val email = "test@test.com"
        val state = LoginState(email = email)

        // Assert
        assert(!state.isEmailError)
    }

    @Test
    fun whenPasswordIsEmptyErrorIsFalse() {
        // Given
        val password = ""
        val state = LoginState(password = password)

        // Assert
        assert(!state.isPasswordError)
    }

    @Test
    fun whenPasswordIsNotEmptyAndPasswordIsValidErrorIsFalse() {
        // Given
        val password = "Pass123!"
        val state = LoginState(password = password)

        // Assert
        assert(!state.isPasswordError)
    }

    @Test
    fun whenPasswordIsNotEmptyAndPasswordIsNotValidErrorIsFalse() {
        // Given
        val password = "password"
        val state = LoginState(password = password)

        // Assert
        assert(state.isPasswordError)
    }

    @Test
    fun whenEmailIsValidAndPasswordIsValidButtonIsEnabled() {
        // Given
        val email = "test@test.com"
        val password = "Pass123!"
        val state = LoginState(email = email, password = password)

        // Assert
        assert(state.isLoginButtonEnabled)
    }
}
