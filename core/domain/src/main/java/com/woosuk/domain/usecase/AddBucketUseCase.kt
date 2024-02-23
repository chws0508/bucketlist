package com.woosuk.domain.usecase

import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.BucketCategory
import com.woosuk.domain.repository.BucketRepository
import java.time.LocalDateTime
import javax.inject.Inject

class AddBucketUseCase @Inject constructor(
    private val bucketRepository: BucketRepository,
) {
    suspend operator fun invoke(
        title: String,
        ageRange: AgeRange,
        bucketCategory: BucketCategory,
        description: String? = null,
        createdAt: LocalDateTime = LocalDateTime.now(),
    ) {
        if (title.isBlank()) return
        bucketRepository.insertBucket(
            Bucket(
                id = 0,
                category = bucketCategory,
                ageRange = ageRange,
                title = title,
                description = description,
                createdAt = createdAt,
                isCompleted = false,
            ),
        )
    }
}
