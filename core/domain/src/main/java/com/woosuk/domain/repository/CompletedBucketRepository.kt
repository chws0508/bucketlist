package com.woosuk.domain.repository

import java.time.LocalDateTime

interface CompletedBucketRepository {
    suspend fun addCompletedBucket(
        bucketId: Int,
        completedDate: LocalDateTime,
        imageUris: List<String>,
        diary: String,
    )
}
