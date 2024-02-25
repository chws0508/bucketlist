package com.woosuk.data.repository

import com.woosuk.database.dao.CompletedBucketDao
import com.woosuk.database.entity.CompletedBucketEntity
import com.woosuk.domain.repository.CompletedBucketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class DefaultCompletedBucketRepository @Inject constructor(
    private val completedBucketDao: CompletedBucketDao,
) : CompletedBucketRepository {
    override suspend fun addCompletedBucket(
        bucketId: Int,
        completedDate: LocalDateTime,
        imageUris: List<String>,
        diary: String,
    ) =
        withContext(Dispatchers.IO) {
            completedBucketDao.insertCompletedBucket(
                CompletedBucketEntity(
                    bucketId = bucketId,
                    completedDate = completedDate,
                    imageUrls = imageUris,
                    description = diary,
                ),
            )
        }
}
