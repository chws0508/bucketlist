package com.woosuk.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.woosuk.database.entity.BucketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BucketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBucket(bucketEntity: BucketEntity)

    @Delete
    suspend fun deleteBucket(bucketEntity: BucketEntity)

    @Delete
    suspend fun deleteBuckets(vararg bucketEntities: BucketEntity)

    @Update
    suspend fun updateBucket(vararg bucketEntities: BucketEntity)

    @Query("SELECT * FROM buckets WHERE id not in (SELECT bucketid FROM completed_buckets)")
    fun loadUnCompletedBuckets(): Flow<List<BucketEntity>>

    @Query("SELECT * FROM buckets")
    fun loadAllBucket(): Flow<List<BucketEntity>>

    @Query("SELECT * FROM buckets WHERE id =:id")
    suspend fun getBucket(id: Int): BucketEntity?
}
