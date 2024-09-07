package com.stori.challenge.data.network.repository.auth

import com.google.firebase.auth.AuthResult
import com.stori.challenge.data.network.datasource.auth.AuthRemoteDataSource
import com.stori.challenge.data.network.utils.SessionManager
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val sessionManager: SessionManager
): AuthRepository {

    override val isUserLogged: Boolean
        get() {
            val user = remoteDataSource.user
            sessionManager.userId = user?.uid ?: ""
            return user != null
        }

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> = flow {
        val result = remoteDataSource.signIn(email, password)
        result.collect {
            if (it is Resource.Success) sessionManager.userId = it.data?.user?.uid ?: ""
            emit(it)
        }
    }

    override suspend fun createUser(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> = remoteDataSource.createUser(email, password)

    override fun signOut() {
        remoteDataSource.signOut()
        sessionManager.clean()
    }
}
