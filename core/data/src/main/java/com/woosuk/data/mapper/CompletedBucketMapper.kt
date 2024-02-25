package com.woosuk.data.mapper

import com.woosuk.database.entity.CompletedBucketEntity
import com.woosuk.domain.model.CompletedBucket

fun CompletedBucket.toEntity() = CompletedBucketEntity(
    bucketId = bucket.id,
    completedDate = completedAt,
    imageUrls = imageUrls,
    description = description,
)
