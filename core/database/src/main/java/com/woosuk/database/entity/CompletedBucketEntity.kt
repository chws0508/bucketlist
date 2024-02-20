package com.woosuk.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "completed_buckets",
    foreignKeys = [
        ForeignKey(
            entity = BucketEntity::class,
            parentColumns = ["id"],
            childColumns = ["bucketId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
    ],
)
data class CompletedBucketEntity(
    @PrimaryKey val bucketId: Int,
    val completionDate: LocalDateTime,
    val imageUrls: List<String>,
    val description: String,
)
