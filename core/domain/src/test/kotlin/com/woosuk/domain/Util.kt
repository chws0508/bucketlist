package com.woosuk.domain

import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.model.BucketCategory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

fun testBucket(
    id: Int,
) = Bucket(
    id = id,
    category = BucketCategory.Career,
    ageRange = AgeRange.Forties,
    title = "antiopam",
    description = null,
    createdAt = LocalDateTime.of(
        LocalDate.of(2023, 5, 30),
        LocalTime.of(20, 5),
    ),
    isCompleted = false,
)
