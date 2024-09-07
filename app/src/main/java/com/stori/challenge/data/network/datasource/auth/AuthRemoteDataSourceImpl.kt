package com.stori.challenge.data.network.datasource.auth

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.stori.challenge.data.network.model.Profile
import com.stori.challenge.domain.model.NetworkError
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): AuthRemoteDataSource {
    override val user: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signIn(
        email: String,
        password: String
    ): Resource<AuthResult> {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            return Resource.Success(result)
        } catch (e: Exception) {
            return Resource.Failure(NetworkError(message = e.message ?: ""))
        }
    }

    override suspend fun createUser(
        email: String,
        password: String,
    ): Resource<AuthResult> {
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            return Resource.Success(result)
        } catch (e: Exception) {
            return Resource.Failure(NetworkError(message = e.message ?: ""))
        }
    }

    override suspend fun register(uid: String): Resource<Unit> {
        val map = mutableMapOf("uid" to uid)
        try {
            firestore.collection("Users").add(map).await()
            return Resource.Success(Unit)
        } catch (e: Exception) {
            return Resource.Failure(NetworkError(message = e.message ?: ""))
        }
    }

    override suspend fun updateProfile(
        user: FirebaseUser?,
        name: String,
        surname: String,
        photo: Uri
    ): Resource<Unit> {
        val request = UserProfileChangeRequest.Builder().apply {
            displayName = "$name $surname"
            photoUri = photo
        }.build()

        try {
            user?.updateProfile(request)?.await()
            return Resource.Success(Unit)
        } catch (e: Exception) {
            return Resource.Failure(NetworkError(message = e.message ?: ""))
        }
    }

    override fun getProfile(): Profile {
        val user = firebaseAuth.currentUser
        return Profile(user?.displayName ?: "", user?.photoUrl)
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }
}