package com.stori.challenge.domain.usecase

import com.stori.challenge.data.network.repository.movements.MovementsRepository
import com.stori.challenge.di.IoDispatcher
import com.stori.challenge.domain.model.Movement
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovementsUseCase @Inject constructor(
    private val repository: MovementsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Flow<Resource<List<Movement>>> = flow {
        emit(Resource.Loading())
        emitAll(repository.getMovements())
    }.map {
        when (it) {
            is Resource.Success -> Resource.Success(
                it.data?.map { it.toMovement() }?.sortedBy { it.date }?.reversed() ?: listOf()
            )
            is Resource.Failure -> Resource.Failure(it.error)
            is Resource.Loading -> Resource.Loading()
        }
    }
    .flowOn(dispatcher)
}