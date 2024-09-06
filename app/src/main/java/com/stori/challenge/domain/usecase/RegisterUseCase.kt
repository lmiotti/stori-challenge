package com.stori.challenge.domain.usecase

import android.net.Uri
import com.stori.challenge.data.network.repository.AuthRepository
import com.stori.challenge.di.IoDispatcher
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val iODispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        name: String,
        surname: String,
        email: String,
        password: String,
        photo: Uri
    ): Flow<Resource<Unit>> {
        return authRepository.register(name, surname, email, password, photo)
            .flowOn(iODispatcher)
    }
}