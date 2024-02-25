package com.woosuk.domain.usecase

import com.woosuk.domain.repository.CompletedBucketRepository
import java.time.LocalDateTime
import javax.inject.Inject

class AddCompletedBucketUseCase @Inject constructor(
    private val completedBucketRepository: CompletedBucketRepository,
) {
    suspend operator fun invoke(
        bucketId: Int,
        completedDate: LocalDateTime,
        imageUris: List<String>,
        diary: String,
    ) {
        completedBucketRepository.addCompletedBucket(
            bucketId,
            completedDate,
            imageUris,
            diary,
        )
    }
}
