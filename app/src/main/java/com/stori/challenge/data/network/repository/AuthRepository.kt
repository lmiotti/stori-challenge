package com.stori.challenge.data.network.repository

import android.net.Uri
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val isUserLogged: Boolean

    suspend fun signIn(
        email: String,
        password: String
    ): Flow<Resource<Unit>>

    suspend fun register(
        form: RegistrationForm
    ): Flow<Resource<Unit>>
}