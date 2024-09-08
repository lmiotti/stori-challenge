package com.stori.challenge.data.network.datasource.movements

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stori.challenge.data.network.model.MovementApiResponse
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MovementsRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): MovementsRemoteDataSource {

    override suspend fun getMovements(): Flow<Resource<List<MovementApiResponse>>> = flow {
        firebaseAuth.currentUser?.uid?.let { uid ->
            try {
                val result = firestore.collection("Movements")
                    .whereEqualTo("userId", uid)
                    .get()
                    .await()
                val movements = result.documents.mapNotNull { it.toObject(MovementApiResponse::class.java) }
                Resource.Success(movements)
            } catch (e: Exception) {
                Resource.Failure(e.message)
            }
        } ?: Resource.Failure("Uid is null")
    }
}