package com.stori.challenge.domain.usecase

import com.stori.challenge.data.network.repository.profile.ProfileRepository
import com.stori.challenge.di.IoDispatcher
import com.stori.challenge.domain.model.Profile
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Flow<Resource<Profile>> = flow {
        emit(Resource.Loading())
        emitAll(repository.getProfile())
    }.map {
        when(it) {
            is Resource.Success -> Resource.Success(it.data?.toProfile() ?: Profile())
            is Resource.Failure -> Resource.Failure(it.error)
            is Resource.Loading -> Resource.Loading()
        }
    }.flowOn(dispatcher)
}