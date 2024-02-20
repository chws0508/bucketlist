package com.woosuk.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.woosuk.database.converter.Converters
import com.woosuk.database.dao.BucketDao
import com.woosuk.database.dao.CompletedBucketDao
import com.woosuk.database.entity.BucketEntity
import com.woosuk.database.entity.CompletedBucketEntity

@Database(entities = [BucketEntity::class, CompletedBucketEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class BucketDatabase : RoomDatabase() {
    abstract fun bucketDao(): BucketDao

    abstract fun completedBucketDao(): CompletedBucketDao
}
