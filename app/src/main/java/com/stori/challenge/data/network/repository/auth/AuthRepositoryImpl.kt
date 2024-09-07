package com.stori.challenge.data.network.repository.auth

import com.google.firebase.auth.FirebaseUser
import com.stori.challenge.data.network.datasource.auth.AuthRemoteDataSource
import com.stori.challenge.data.network.model.Profile
import com.stori.challenge.data.network.utils.SessionManager
import com.stori.challenge.domain.model.RegistrationForm
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
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val result = remoteDataSource.signIn(email, password)
        if (result is Resource.Success) {
            sessionManager.userId = result.data?.user?.uid ?: ""
            emit(Resource.Success(Unit))
        } else {
            emit(Resource.Failure(result.error!!))
        }
    }

    override suspend fun createUser(
        form: RegistrationForm
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val result = remoteDataSource.createUser(form.email, form.password)
        if (result is Resource.Success) {
            updateProfile(result.data?.user, form)
        } else {
            emit(Resource.Failure(result.error!!))
        }
    }

    private suspend fun updateProfile(user: FirebaseUser?, form: RegistrationForm): Resource<Unit> {
        val result = remoteDataSource.updateProfile(user, form.name, form.surname, form.photo!!)
        return if (result is Resource.Success) {
            register(user?.uid ?: "")
        } else {
            signOut()
            Resource.Failure(result.error!!)
        }
    }

    private suspend fun register(uid: String): Resource<Unit> {
        val result = remoteDataSource.register(uid)
        return if (result is Resource.Success) {
            Resource.Success(Unit)
        } else {
            signOut()
            Resource.Failure(result.error!!)
        }
    }

    override fun getProfile(): Profile {
        return remoteDataSource.getProfile()
    }

    override suspend fun signOut() {
        remoteDataSource.signOut()
        sessionManager.clean()
    }
}
