package com.stori.challenge.data.network.repository

import android.net.Uri
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(
        email: String,
        password: String
    ): Flow<Resource<Boolean>>
    suspend fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        photo: Uri
    ): Flow<Resource<Unit>>
}