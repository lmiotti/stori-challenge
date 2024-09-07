package com.stori.challenge.data.network.datasource.movements

import com.google.firebase.firestore.FirebaseFirestore
import com.stori.challenge.data.network.model.MovementApiResponse
import com.stori.challenge.data.network.utils.SessionManager
import com.stori.challenge.domain.model.NetworkError
import com.stori.challenge.domain.model.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class MovementsRemoteDataSourceImpl @Inject constructor(
    private val sessionManager: SessionManager,
    private val firestore: FirebaseFirestore
): MovementsRemoteDataSource {

    override suspend fun getMovements(): Resource<List<MovementApiResponse>> {
        return try {
            val result = firestore.collection("Users")
                .document(sessionManager.userId)
                .collection("movements")
                .get()
                .await()
            val movements = result.documents.mapNotNull { it.toObject(MovementApiResponse::class.java) }
            Resource.Success(movements)
        } catch (e: Exception) {
            Resource.Failure(NetworkError(message = e.message ?: ""))
        }
    }
}