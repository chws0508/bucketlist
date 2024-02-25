package com.woosuk.domain.usecase

import com.woosuk.domain.model.Bucket
import com.woosuk.domain.repository.BucketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateBucketUseCase @Inject constructor(
    private val bucketRepository: BucketRepository,
) {
    suspend operator fun invoke(bucket: Bucket) = withContext(Dispatchers.IO) {
        bucketRepository.updateBucket(bucket)
    }
}
