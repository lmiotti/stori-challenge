package com.stori.challenge.viewmodel

import app.cash.turbine.test
import com.stori.challenge.BaseCoroutineTest
import com.stori.challenge.domain.model.Resource
import com.stori.challenge.domain.usecase.SignInUseCase
import com.stori.challenge.presentation.ui.intent.LoginIntent
import com.stori.challenge.presentation.ui.state.LoginState
import com.stori.challenge.presentation.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest: BaseCoroutineTest() {

    @Mock
    private lateinit var useCase: SignInUseCase

    private lateinit var viewModel: LoginViewModel

    override fun afterSetup() {
        super.afterSetup()
        viewModel = LoginViewModel(useCase, dispatcher)
    }

    @Test
    fun whenOnEmailChanged_thenEmailIsUpdated() {
        runTest {
            // Given
            val email = "test@test.com"

            // When
            viewModel.handleIntent(LoginIntent.OnEmailChanged(email = email))

            // Then
            viewModel.state.test {
                Assert.assertEquals(LoginState().copy(email = email), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun whenOnPasswordChanged_thenEmailIsUpdated() {
        runTest {
            // Given
            val password = "password"

            // When
            viewModel.handleIntent(LoginIntent.OnPasswordChanged(password = password))

            // Then
            viewModel.state.test {
                Assert.assertEquals(LoginState().copy(password = password), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun whenOnLoginClickedAndResourceIsLoading_thenStateIsLoading() {
        runTest {
            // Given
            val email = "test@test.com"
            val password = "password"

            doReturn(flowOf(Resource.Loading<Unit>())).`when`(useCase).invoke(email, password)

            // When
            viewModel.handleIntent(LoginIntent.OnEmailChanged(email))
            viewModel.handleIntent(LoginIntent.OnPasswordChanged(password))
            viewModel.handleIntent(LoginIntent.OnLoginClicked)

            // Then
            viewModel.state.test {
                Assert.assertEquals(
                    LoginState(isLoading = true, email = email, password = password),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun whenOnLoginClickedAndResourceIsSuccess_thenGoToHome() {
        runTest {
            // Given
            val email = "test@test.com"
            val password = "password"
            doReturn(flowOf(Resource.Success(Unit))).`when`(useCase).invoke(email, password)
            viewModel.handleIntent(LoginIntent.OnEmailChanged(email))
            viewModel.handleIntent(LoginIntent.OnPasswordChanged(password))

            // When
            viewModel.handleIntent(LoginIntent.OnLoginClicked)

            // Then
            viewModel.state.test {
                Assert.assertEquals(
                    LoginState(isLoading = false, email = email, password = password),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }

            verify(useCase).invoke(email, password)
        }
    }

    @Test
    fun whenOnLoginClickedAndResourceIsFailure_thenShowError() {
        runTest {
            // Given
            val email = "test@test.com"
            val password = "password"
            val error = "error"
            doReturn(flowOf(Resource.Success(error))).`when`(useCase).invoke(email, password)
            viewModel.handleIntent(LoginIntent.OnEmailChanged(email))
            viewModel.handleIntent(LoginIntent.OnPasswordChanged(password))

            // When
            viewModel.handleIntent(LoginIntent.OnLoginClicked)
            advanceUntilIdle()

            // Then
            viewModel.state.test {
                Assert.assertEquals(
                    LoginState(isLoading = false, email = email, password = password),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }

            verify(useCase).invoke(email, password)
        }
    }
}
