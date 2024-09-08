package com.stori.challenge.data.network.datasource.auth

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.StorageReference
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: StorageReference
): AuthRemoteDataSource {
    override val user: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> = flow {
        val result = try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Failure(e.message)
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
            Resource.Failure(e.message)
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
            Resource.Failure(e.message)
        }
        emit(result)
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}