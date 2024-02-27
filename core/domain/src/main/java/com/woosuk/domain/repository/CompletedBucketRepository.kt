package com.woosuk.domain.repository

import com.woosuk.domain.model.CompletedBucket
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface CompletedBucketRepository {
    suspend fun addCompletedBucket(
        bucketId: Int,
        completedDate: LocalDateTime,
        imageUris: List<String>,
        diary: String,
    )

    suspend fun deleteCompletedBucket(completedBucket: CompletedBucket)

    suspend fun getCompletedBucket(bucketId: Int): CompletedBucket

    suspend fun updateCompletedBucket(completedBucket: CompletedBucket)

    fun getAllCompletedBucket(): Flow<List<CompletedBucket>>
}
