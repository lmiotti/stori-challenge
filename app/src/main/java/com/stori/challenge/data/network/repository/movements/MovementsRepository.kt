package com.stori.challenge.data.network.repository.movements

import com.stori.challenge.data.network.model.MovementDTO
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface MovementsRepository {

    suspend fun getMovements(): Flow<Resource<List<MovementDTO>>>
}