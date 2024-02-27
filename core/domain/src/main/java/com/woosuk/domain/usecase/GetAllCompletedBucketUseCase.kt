package com.woosuk.domain.usecase

import com.woosuk.domain.model.CompletedBucket
import com.woosuk.domain.repository.CompletedBucketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCompletedBucketUseCase @Inject constructor(
    private val completedBucketRepository: CompletedBucketRepository,
) {
    operator fun invoke(): Flow<List<CompletedBucket>> {
        return completedBucketRepository.getAllCompletedBucket()
    }
}
