package com.woosuk.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.woosuk.database.entity.CompletedBucketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletedBucketDao {
    @Insert
    suspend fun insertCompletedBucket(completedBucketEntity: CompletedBucketEntity)

    @Delete
    suspend fun deleteCompletedBucket(completedBucketEntity: CompletedBucketEntity)

    @Update
    suspend fun updateCompletedBucket(completedBucketEntity: CompletedBucketEntity)

    @Query("SELECT * FROM completed_buckets")
    fun loadCompletedBuckets(): Flow<List<CompletedBucketEntity>>

    @Query("SELECT * FROM completed_buckets WHERE bucketId =:bucketId")
    suspend fun getCompletedBucket(bucketId: Int): CompletedBucketEntity
}
