package com.woosuk.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Bucket(
    val id: Int,
    val category: BucketCategory,
    val ageRange: AgeRange,
    val title: String,
    val description: String? = null,
    val createdAt: LocalDateTime,
    val isCompleted: Boolean,
) {
    init {
        require(title.isNotBlank()) { "제목은 빈칸일 수 없어요" }
    }
    companion object {
        fun mock(
            id: Int = 1
        ): Bucket =
            Bucket(
                id = id,
                category = BucketCategory.Health,
                ageRange = AgeRange.Twenties,
                title = "미국 여행 가기",
                description = "뉴욕가서 릴스 찍기",
                createdAt = LocalDateTime.of(
                    LocalDate.of(1, 1, 1),
                    LocalTime.of(1, 1),
                ),
                isCompleted = false,
            )
    }
}
