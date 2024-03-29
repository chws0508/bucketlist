package com.woosuk.data.di

import com.woosuk.data.repository.DefaultBucketRepository
import com.woosuk.data.repository.DefaultCompletedBucketRepository
import com.woosuk.domain.repository.BucketRepository
import com.woosuk.domain.repository.CompletedBucketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindsDefaultBucketRepository(
        defaultBucketRepository: DefaultBucketRepository,
    ): BucketRepository

    @Binds
    @Singleton
    fun bindsDefaultCompletedBucketRepository(
        defaultCompletedBucketRepository: DefaultCompletedBucketRepository,
    ): CompletedBucketRepository
}
