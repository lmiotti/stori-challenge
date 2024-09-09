package com.stori.challenge.viewmodel

import app.cash.turbine.test
import com.stori.challenge.BaseCoroutineTest
import com.stori.challenge.domain.usecase.IsUserLoggedUseCase
import com.stori.challenge.presentation.ui.viewmodel.SplashViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest: BaseCoroutineTest() {

    @Mock
    private lateinit var useCase: IsUserLoggedUseCase

    @Test
    fun whenUserIsNotLogged_thenIsUserLoggedIsFalse() = runTest {
        // Given
        val isLogged = false
        doReturn(isLogged).`when`(useCase).invoke()

        // When
        val viewModel = SplashViewModel(useCase, dispatcher)

        // Then
        viewModel.isUserLogged.test {
            Assert.assertEquals(false, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        verify(useCase).invoke()
    }

    @Test
    fun whenUserIsLogged_thenIsUserLoggedIsTrue() = runTest {
        // Given
        val isLogged = true
        doReturn(isLogged).`when`(useCase).invoke()

        // When
        val viewModel = SplashViewModel(useCase, dispatcher)

        // Then
        viewModel.isUserLogged.test {
            Assert.assertEquals(true, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        verify(useCase).invoke()
    }
}
