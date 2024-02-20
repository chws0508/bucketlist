package com.woosuk.domain.usecase

import com.woosuk.domain.model.Buckets
import com.woosuk.domain.repository.BucketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBucketsUseCase @Inject constructor(
    private val bucketRepository: BucketRepository,
) {
    suspend operator fun invoke(): Flow<Buckets> = bucketRepository.getAllBuckets()
}
