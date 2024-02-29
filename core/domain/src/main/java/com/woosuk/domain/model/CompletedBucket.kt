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
                category = BucketCategory.Career,
                ageRange = AgeRange.UnSpecified,
                title = "취업 성공하기!!!! ",
                description = "공부하기 밥먹기 자기",
                createdAt = LocalDateTime.of(
                    LocalDate.of(2024, 2, 10),
                    LocalTime.of(1, 1),
                ),
                isCompleted = true,
            ),
            completedAt = LocalDateTime.of(
                LocalDate.of(2024, 2, 18),
                LocalTime.of(1, 1),
            ),
            imageUrls = listOf("","",""),
            description = "너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!",
        )

        private const val SHOULD_IS_COMPLETED_STATE_TRUE =
            " Bucket의 완료 상태는 true여야 합니다!!"
    }
}
