package com.stori.challenge.data.network.repository

import android.net.Uri
import com.stori.challenge.data.network.datasource.AuthRemoteDataSource
import com.stori.challenge.domain.model.RegistrationForm
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
): AuthRepository {

    override val isUserLogged = authRemoteDataSource.isUserLogged

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val result = authRemoteDataSource.signIn(email, password)
        if (result is Resource.Success) {
            //authLocalDataSource.saveEmail(result.data?.user?.email ?: "")
            emit(Resource.Success(Unit))
        } else {
            emit(Resource.Failure(result.error!!))
        }
    }

    override suspend fun register(
        form: RegistrationForm
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val result = authRemoteDataSource.createUser(form.email, form.password)
        if (result is Resource.Success) {
            emit(authRemoteDataSource.updateProfile(result.data?.user, form.name, form.surname, form.photo!!))
        } else {
            emit(Resource.Failure(result.error!!))
        }
    }
}
