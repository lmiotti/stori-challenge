package com.stori.challenge.data.network.repository.movements

import com.stori.challenge.data.network.datasource.movements.MovementsRemoteDataSource
import com.stori.challenge.data.network.model.MovementDTO
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovementsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovementsRemoteDataSource
): MovementsRepository {

    override suspend fun getMovements(): Flow<Resource<List<MovementDTO>>> = remoteDataSource.getMovements()
}