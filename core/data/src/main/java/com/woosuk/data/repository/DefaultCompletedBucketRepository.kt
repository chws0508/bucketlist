package com.woosuk.data.repository

import com.woosuk.data.mapper.toDomain
import com.woosuk.data.mapper.toEntity
import com.woosuk.database.dao.BucketDao
import com.woosuk.database.dao.CompletedBucketDao
import com.woosuk.database.entity.CompletedBucketEntity
import com.woosuk.domain.model.CompletedBucket
import com.woosuk.domain.repository.CompletedBucketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class DefaultCompletedBucketRepository @Inject constructor(
    private val bucketDao: BucketDao,
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

    override suspend fun deleteCompletedBucket(completedBucket: CompletedBucket) =
        withContext(Dispatchers.IO) {
            completedBucketDao.deleteCompletedBucket(completedBucket.toEntity())
        }

    override suspend fun getCompletedBucket(bucketId: Int): CompletedBucket =
        withContext(Dispatchers.IO) {
            val completedBucketEntity = completedBucketDao.getCompletedBucket(bucketId)
            val bucket =
                bucketDao.getBucket(bucketId)?.toDomain() ?: throw IllegalStateException()
            CompletedBucket(
                bucket = bucket, completedAt = completedBucketEntity.completedDate,
                imageUrls = completedBucketEntity.imageUrls,
                description = completedBucketEntity.description,
            )
        }

    override fun getAllCompletedBucket(): Flow<List<CompletedBucket>> = channelFlow {
        completedBucketDao.loadCompletedBuckets().map { entities ->
            entities.map { entitiy ->
                val bucket = bucketDao.getBucket(entitiy.bucketId) ?: throw IllegalStateException()
                CompletedBucket(
                    bucket = bucket.toDomain(),
                    completedAt = entitiy.completedDate,
                    imageUrls = entitiy.imageUrls,
                    description = entitiy.description,
                )
            }
        }.collectLatest {
            send(it)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateCompletedBucket(completedBucket: CompletedBucket) =
        withContext(Dispatchers.IO) {
            completedBucketDao.updateCompletedBucket(completedBucket.toEntity())
        }
}
