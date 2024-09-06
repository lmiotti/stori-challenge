package com.stori.challenge.domain.usecase

import com.stori.challenge.data.network.repository.AuthRepository
import com.stori.challenge.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IsUserLoggedUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Boolean = withContext(dispatcher) {
        authRepository.isUserLogged
    }
}