package com.woosuk.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class CompletedBucket(
    val bucket: Bucket,
    val completedAt: LocalDateTime,
    val imageUrls: List<String>,
    val description: String,
) {
    init {
        require(bucket.isCompleted) { SHOULD_IS_COMPLETED_STATE_TRUE }
    }

    companion object {
        fun mock(
            id: Int,
        ) = CompletedBucket(
            bucket = Bucket(
                id = id,
                category = BucketCategory.Work,
                ageRange = AgeRange.UnSpecified,
                title = "orci",
                description = null,
                createdAt = LocalDateTime.of(
                    LocalDate.of(1, 1, 1),
                    LocalTime.of(1, 1),
                ),
                isCompleted = true,
            ),
            completedAt = LocalDateTime.of(
                LocalDate.of(1, 1, 1),
                LocalTime.of(1, 1),
            ),
            imageUrls = listOf(),
            description = "",
        )

        private const val SHOULD_IS_COMPLETED_STATE_TRUE =
            " Bucket의 완료 상태는 true여야 합니다!!"
    }
}
