package com.stori.challenge.data.network.datasource.movements

import com.stori.challenge.data.network.model.MovementApiResponse
import com.stori.challenge.domain.model.Resource

interface MovementsRemoteDataSource {

    suspend fun getMovements(): Resource<List<MovementApiResponse>>
}