package com.woosuk.data.mapper

import com.woosuk.database.entity.BucketEntity
import com.woosuk.domain.model.Bucket

fun BucketEntity.toDomain() = Bucket(
    id = id,
    category = category,
    ageRange = ageRange,
    title = title,
    description = description,
    createdAt = createdAt,
    isCompleted = isCompleted,
)

fun Bucket.toEntity() = BucketEntity(
    id = id,
    category = category,
    ageRange = ageRange,
    title = title,
    description = description,
    createdAt = createdAt,
    isCompleted = isCompleted,
)
