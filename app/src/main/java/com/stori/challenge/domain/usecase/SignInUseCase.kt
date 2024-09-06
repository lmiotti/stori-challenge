package com.stori.challenge.domain.usecase

import com.stori.challenge.data.network.repository.AuthRepository
import com.stori.challenge.di.IoDispatcher
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val iODispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): Flow<Resource<Unit>> {
        return authRepository.signIn(email, password)
            .flowOn(iODispatcher)
    }

}