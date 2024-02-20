package com.woosuk.database

import com.woosuk.database.entity.BucketEntity
import com.woosuk.database.entity.CompletedBucketEntity
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.BucketCategory
import java.time.LocalDateTime

fun testBucketEntity(
    id: Int,
    title: String,
) = BucketEntity(
    id = id,
    title = title,
    description = null,
    category = BucketCategory.Work,
    ageRange = AgeRange.Forties,
    createdAt = LocalDateTime.now(),
    isCompleted = true,
)

fun testCompletedBucketEntity(
    id: Int,
    description: String = "test"
) = CompletedBucketEntity(
    bucketId = id,
    completionDate = LocalDateTime.now(),
    imageUrls = listOf(),
    description = description,
)
