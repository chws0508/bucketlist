package com.woosuk.domain.usecase

import com.woosuk.domain.model.CompletedBucket
import com.woosuk.domain.repository.CompletedBucketRepository
import javax.inject.Inject

class DeleteCompletedBucketUseCase @Inject constructor(
    private val deleteBucketUseCase: DeleteBucketUseCase,
    private val completedBucketRepository: CompletedBucketRepository,
) {
    suspend operator fun invoke(completedBucket: CompletedBucket) {
        completedBucketRepository.deleteCompletedBucket(completedBucket)
        deleteBucketUseCase(completedBucket.bucket)
    }
}
