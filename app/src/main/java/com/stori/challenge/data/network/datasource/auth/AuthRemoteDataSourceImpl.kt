package com.stori.challenge.data.network.datasource.auth

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.stori.challenge.data.network.model.Profile
import com.stori.challenge.domain.model.NetworkError
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: StorageReference
): AuthRemoteDataSource {
    override val user: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> = flow {
        Log.e("ASD", "DataSource")
        val result = try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Failure(NetworkError(message = e.message ?: ""))
        }
        emit(result)
    }

    override suspend fun createUser(
        email: String,
        password: String,
    ): Flow<Resource<AuthResult>> = flow {
        val result = try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Failure(NetworkError(message = e.message ?: ""))
        }
        emit(result)
    }

    override suspend fun uploadImage(image: Uri): Flow<Resource<String>> = flow {
        val imageRef = firebaseStorage.child("images/${UUID.randomUUID()}")
        val result = try {
            val taskSnapshot = imageRef.putFile(image).await()
            val downloadUri = taskSnapshot.metadata?.reference?.downloadUrl?.await()
            Resource.Success(data = downloadUri.toString())
        } catch (e: Exception) {
            Resource.Failure(networkError = NetworkError(message = e.message ?: ""))
        }
        emit(result)
    }

    override suspend fun updateProfile(
        user: FirebaseUser?,
        name: String,
        surname: String,
        photoPath: String
    ): Flow<Resource<Unit>> = flow {
        val map = mutableMapOf(
            "uid" to user?.uid,
            "name" to name,
            "surname" to surname
        )

        val result = try {
            firestore.collection("Users").add(map).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(NetworkError(message = e.message ?: ""))
        }
        emit(result)
    }

    override fun getProfile(): Profile {
        val user = firebaseAuth.currentUser
        return Profile(user?.displayName ?: "", user?.photoUrl)
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}