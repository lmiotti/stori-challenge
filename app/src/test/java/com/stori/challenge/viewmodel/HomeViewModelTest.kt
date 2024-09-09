package com.stori.challenge.viewmodel

import app.cash.turbine.test
import com.stori.challenge.BaseCoroutineTest
import com.stori.challenge.domain.model.Movement
import com.stori.challenge.domain.model.Profile
import com.stori.challenge.domain.model.Resource
import com.stori.challenge.domain.usecase.GetMovementsUseCase
import com.stori.challenge.domain.usecase.GetProfileUseCase
import com.stori.challenge.domain.usecase.SignOutUseCase
import com.stori.challenge.presentation.ui.state.HomeState
import com.stori.challenge.presentation.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest: BaseCoroutineTest() {

    @Mock
    private lateinit var profileUseCase: GetProfileUseCase

    @Mock
    private lateinit var movementsUseCase: GetMovementsUseCase

    @Mock
    private lateinit var signOutUseCase: SignOutUseCase

    private lateinit var viewModel: HomeViewModel

    @Test
    fun whenProfileAndMovementsAreSuccess_thenProfileAndMovementsAreUpdated() = runTest {
        val movement = Movement(
            id = "123",
            date = Date(),
            amount = 123F,
            description = "Description",
            state = "Pending",
            type = "Transfer"
        )
        val profile = Profile(name = "test")
        val balance = 123F

        // Given
        doReturn(flowOf(Resource.Success(profile))).`when`(profileUseCase).invoke()
        doReturn(flowOf(Resource.Success(listOf(movement)))).`when`(movementsUseCase).invoke()

        // When
        viewModel = HomeViewModel(profileUseCase, movementsUseCase, signOutUseCase, dispatcher)

        dispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            Assert.assertEquals(
                HomeState(name = profile.name, movements = listOf(movement), balance = balance),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}
