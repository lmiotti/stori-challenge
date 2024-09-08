package com.stori.challenge.data.network.datasource.movements

import com.stori.challenge.data.network.model.MovementDTO
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface MovementsRemoteDataSource {

    suspend fun getMovements(): Flow<Resource<List<MovementDTO>>>
}