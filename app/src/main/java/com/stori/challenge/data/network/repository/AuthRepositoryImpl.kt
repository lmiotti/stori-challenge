package com.stori.challenge.data.network.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.stori.challenge.data.network.datasource.AuthRemoteDataSource
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
): AuthRepository {

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val result = authRemoteDataSource.signIn(email, password)
        if (result is Resource.Success) {
            // Save locally key
            emit(Resource.Success(true))
        } else {
            emit(Resource.Failure(result.error!!))
        }
    }

    override suspend fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        photo: Uri
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val result = authRemoteDataSource.createUser(email, password)
        if (result is Resource.Success) {
            emit(authRemoteDataSource.updateProfile(result.data?.user, name, surname, photo))
        } else {
            emit(Resource.Failure(result.error!!))
        }
    }
}
