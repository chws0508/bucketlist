package com.woosuk.domain.usecase

import com.woosuk.domain.model.CompletedBucket
import com.woosuk.domain.repository.CompletedBucketRepository
import javax.inject.Inject

class UpdateCompletedBucketUseCase @Inject constructor(
    private val completedBucketRepository: CompletedBucketRepository
) {
    suspend operator fun invoke(completedBucket: CompletedBucket) {
        completedBucketRepository.updateCompletedBucket(completedBucket)
    }
}
