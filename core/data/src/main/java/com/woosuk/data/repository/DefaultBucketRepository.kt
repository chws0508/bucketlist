package com.woosuk.data.repository

import com.woosuk.data.mapper.toDomain
import com.woosuk.data.mapper.toEntity
import com.woosuk.database.dao.BucketDao
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.Buckets
import com.woosuk.domain.repository.BucketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class DefaultBucketRepository @Inject constructor(
    private val bucketDao: BucketDao,
) : BucketRepository {

    override fun getAllBuckets(): Flow<Buckets> = channelFlow {
        bucketDao.loadAllBucket().collectLatest { bucketEntities ->
            val buckets = Buckets(
                bucketEntities.map { it.toDomain() },
            )
            send(buckets)
        }
    }

    override suspend fun deleteBucket(bucket: Bucket) {
        bucketDao.deleteBucket(bucket.toEntity())
    }

    override suspend fun insertBucket(bucket: Bucket) {
        bucketDao.insertBucket(bucket.toEntity())
    }

    override suspend fun getBucket(id: Int): Bucket? = bucketDao.getBucket(id)?.toDomain()

    override suspend fun updateBucket(bucket: Bucket) {
        bucketDao.updateBucket(bucket.toEntity())
    }
}
