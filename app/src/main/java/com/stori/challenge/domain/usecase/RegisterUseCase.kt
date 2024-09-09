package com.stori.challenge.domain.usecase

import com.stori.challenge.data.network.repository.auth.AuthRepository
import com.stori.challenge.data.network.repository.profile.ProfileRepository
import com.stori.challenge.di.IoDispatcher
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    @IoDispatcher private val iODispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(form: RegistrationForm): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val response = authRepository.createUser(form.email, form.password)
        response.collect {
            when (it) {
                is Resource.Success -> emitAll(uploadImage(form))
                is Resource.Failure -> emit(Resource.Failure<Unit>(it.error))
                is Resource.Loading -> emit(Resource.Loading<Unit>())
            }
        }
    }.flowOn(iODispatcher)

    private suspend fun uploadImage(
        form: RegistrationForm
    ): Flow<Resource<Unit>> = flow {
        val response = profileRepository.uploadImage(form.photo)
        response.collect {
            when (it) {
                is Resource.Success -> emitAll(updateProfile(form, it.data ?: ""))
                is Resource.Failure -> emit(Resource.Failure(it.error))
                is Resource.Loading -> emit(Resource.Loading())
            }
        }
    }

    private suspend fun updateProfile(
        form: RegistrationForm,
        imagePath: String
    ): Flow<Resource<Unit>> = profileRepository.updateProfile(form, imagePath)
}