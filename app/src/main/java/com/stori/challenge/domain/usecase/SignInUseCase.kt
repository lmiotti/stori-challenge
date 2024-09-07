package com.stori.challenge.domain.usecase

import com.stori.challenge.data.network.repository.auth.AuthRepository
import com.stori.challenge.di.IoDispatcher
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val iODispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading<Unit>())
        val result = authRepository.signIn(email, password)
        result.collect {
            when (it) {
                is Resource.Success -> {
                    emit(Resource.Success(Unit))
                }
                is Resource.Failure -> {
                    emit(Resource.Failure<Unit>(it.error!!))
                }
                is Resource.Loading -> {
                    emit(Resource.Loading<Unit>())
                }
            }
        }
    }.flowOn(iODispatcher)
}