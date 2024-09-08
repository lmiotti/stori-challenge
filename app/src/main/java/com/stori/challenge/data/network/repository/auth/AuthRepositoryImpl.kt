package com.stori.challenge.data.network.repository.auth

import com.google.firebase.auth.AuthResult
import com.stori.challenge.data.network.datasource.auth.AuthRemoteDataSource
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
): AuthRepository {

    override val isUserLogged: Boolean
        get() = remoteDataSource.user != null

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> = remoteDataSource.signIn(email, password)

    override suspend fun createUser(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> = remoteDataSource.createUser(email, password)

    override fun signOut() {
        remoteDataSource.signOut()
    }
}
