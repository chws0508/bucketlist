package com.woosuk.domain.usecase

import com.woosuk.domain.model.CompletedBucket
import com.woosuk.domain.repository.CompletedBucketRepository
import javax.inject.Inject

class GetCompletedBucketUseCase @Inject constructor(
    private val completedBucketRepository: CompletedBucketRepository,
) {
    suspend operator fun invoke(bucketId:Int):CompletedBucket {
        return completedBucketRepository.getCompletedBucket(bucketId)
    }
}
