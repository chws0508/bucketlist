package com.woosuk.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class Buckets(
    initValue: List<Bucket>,
) {
    private val _value = initValue
    val value: List<Bucket>
        get() = _value.toList()

    val completedCount = value.filter { it.isCompleted }.size

    fun getBucketListByCategory(bucketCategory: BucketCategory) =
        value.filter { it.category == bucketCategory }

    fun getBucketListByAgeRange(ageRange: AgeRange) =
        value.filter { it.ageRange == ageRange }

    fun getAllAchievementRate(): Double {
        if (value.isEmpty()) return 0.0
        val completedBuckets = value.filter { it.isCompleted }
        return completedBuckets.size.toDouble() / value.size * 100
    }

    fun getAchievementRateByCategory(bucketCategory: BucketCategory): Double {
        val categoryBuckets = getBucketListByCategory(bucketCategory)
        if (categoryBuckets.isEmpty()) return 0.0
        val completedCategoryBuckets = categoryBuckets.filter { it.isCompleted }
        return completedCategoryBuckets.size.toDouble() / categoryBuckets.size * 100
    }

    fun getAchievementRateByAgeRange(ageRange: AgeRange): Double {
        val ageRangeBuckets = getBucketListByAgeRange(ageRange)
        if (ageRangeBuckets.isEmpty()) return 0.0
        val completedCategoryBuckets = ageRangeBuckets.filter { it.isCompleted }
        return completedCategoryBuckets.size.toDouble() / ageRangeBuckets.size * 100
    }

    companion object {
        fun mock(): Buckets =
            Buckets(
                List(15) {
                    Bucket(
                        id = it,
                        category = BucketCategory.Career,
                        ageRange = AgeRange.OldAge,
                        title = "통장에 5000만원 모으기!통장에 5000만원 모으기!통장에 5000만원 모으기!",
                        description = "너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!너무 좋았다!!!",
                        isCompleted = true,
                        createdAt = LocalDateTime.of(
                            LocalDate.of(2023, 5, 3),
                            LocalTime.of(10, 20),
                        ),
                    )
                },
            )
    }
}
