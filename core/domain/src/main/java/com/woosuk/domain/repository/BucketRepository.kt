package com.woosuk.domain.repository

import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.Buckets
import kotlinx.coroutines.flow.Flow

interface BucketRepository {
    fun getAllBuckets(): Flow<Buckets>

    suspend fun deleteBucket(bucket: Bucket)

    suspend fun insertBucket(bucket: Bucket)
}
