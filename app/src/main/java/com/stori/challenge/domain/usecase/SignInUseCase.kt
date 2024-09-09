package com.stori.challenge.domain.usecase

import com.stori.challenge.data.network.repository.auth.AuthRepository
import com.stori.challenge.di.IoDispatcher
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
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
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading<Unit>())
        emitAll(authRepository.signIn(email, password))
    }
    .map {
        when (it) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Failure -> Resource.Failure(it.error!!)
            is Resource.Loading -> Resource.Loading()
        }
    }
    .flowOn(iODispatcher)
}
