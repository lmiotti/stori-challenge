package com.stori.challenge.domain.usecase

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.stori.challenge.data.network.repository.auth.AuthRepository
import com.stori.challenge.data.network.repository.profile.ProfileRepository
import com.stori.challenge.di.IoDispatcher
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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
        val result = authRepository.createUser(form.email, form.password)
        result.collect {
            when (it) {
                is Resource.Success -> emitAll(uploadImage(it.data?.user, form))
                is Resource.Failure -> emit(Resource.Failure<Unit>(networkError = it.error!!))
                is Resource.Loading -> emit(Resource.Loading<Unit>())
            }
        }
    }.flowOn(iODispatcher)

    private suspend fun uploadImage(
        user: FirebaseUser?,
        form: RegistrationForm
    ): Flow<Resource<Unit>> = flow {
        val result = profileRepository.uploadImage(user, form.photo ?: Uri.parse(""))
        result.collect {
            when (it) {
                is Resource.Success -> emitAll(updateProfile(user, form, it.data ?: ""))
                is Resource.Failure -> emit(Resource.Failure(networkError = it.error!!))
                is Resource.Loading -> emit(Resource.Loading())
            }
        }
    }

    private suspend fun updateProfile(
        user: FirebaseUser?,
        form: RegistrationForm,
        imagePath: String
    ): Flow<Resource<Unit>> = profileRepository.updateProfile(user, form, imagePath)
}