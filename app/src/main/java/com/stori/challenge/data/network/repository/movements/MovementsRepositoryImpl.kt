package com.stori.challenge.data.network.repository.movements

import com.stori.challenge.data.network.datasource.movements.MovementsRemoteDataSource
import com.stori.challenge.data.network.model.MovementApiResponse
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovementsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovementsRemoteDataSource
): MovementsRepository {

    override suspend fun getMovements(): Flow<Resource<List<MovementApiResponse>>> = remoteDataSource.getMovements()
}