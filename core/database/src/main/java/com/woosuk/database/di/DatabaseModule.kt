package com.woosuk.database.di

import android.content.Context
import androidx.room.Room
import com.woosuk.database.BucketDatabase
import com.woosuk.database.dao.BucketDao
import com.woosuk.database.dao.CompletedBucketDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBucketDataBase(
        @ApplicationContext context: Context,
    ): BucketDatabase = Room.databaseBuilder(
        context,
        BucketDatabase::class.java,
        "bucket_db",
    ).build()

    @Provides
    @Singleton
    fun provideBucketDao(
        bucketDatabase: BucketDatabase,
    ): BucketDao = bucketDatabase.bucketDao()

    @Provides
    @Singleton
    fun provideCompletedBucketDao(
        bucketDatabase: BucketDatabase,
    ): CompletedBucketDao = bucketDatabase.completedBucketDao()
}
