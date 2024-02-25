package com.woosuk.domain.usecase

import com.woosuk.domain.model.Bucket
import com.woosuk.domain.repository.BucketRepository
import javax.inject.Inject

class GetBucketUseCase @Inject constructor(
    private val bucketRepository: BucketRepository,
) {
    suspend operator fun invoke(id: Int): Bucket? = bucketRepository.getBucket(id)
}
