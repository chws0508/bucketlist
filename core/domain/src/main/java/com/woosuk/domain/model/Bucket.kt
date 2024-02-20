package com.woosuk.domain.model

import java.time.LocalDateTime

data class Bucket(
    val id: Int,
    val category: BucketCategory,
    val ageRange: AgeRange,
    val title: String,
    val description: String? = null,
    val createdAt: LocalDateTime,
    val isCompleted: Boolean,
) {
    companion object {
        fun mock(): Bucket =
            Bucket(
                id = 1,
                category = BucketCategory.Health,
                ageRange = AgeRange.Twenties,
                title = "미국 여행 가기",
                description = "뉴욕가서 릴스 찍기",
                createdAt = LocalDateTime.now(),
                isCompleted = false,
            )
    }
}

class Buckets(
    initValue: List<Bucket>,
) {
    private val _value = initValue
    val value: List<Bucket>
        get() = _value.toList()

    fun getBucketListByCategory(bucketCategory: BucketCategory) =
        value.filter { it.category == bucketCategory }

    companion object {
        fun mock(): Buckets =
            Buckets(
                List(15) {
                    Bucket(
                        id = it,
                        category = BucketCategory.Work,
                        ageRange = AgeRange.OldAge,
                        title = "통장에 5000만원 모으기!",
                        description = "너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!",
                        isCompleted = false,
                        createdAt = LocalDateTime.now(),
                    )
                },
            )
    }
}
