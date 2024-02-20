package com.woosuk.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.BucketCategory
import java.time.LocalDateTime

@Entity(tableName = "buckets")
data class BucketEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val title: String,
    val description: String?,
    val category: BucketCategory,
    val ageRange: AgeRange,
    val createdAt: LocalDateTime,
    val isCompleted: Boolean,
)
