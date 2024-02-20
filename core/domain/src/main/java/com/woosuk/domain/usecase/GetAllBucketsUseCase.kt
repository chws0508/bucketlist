package com.woosuk.domain.usecase

import com.woosuk.domain.model.Buckets
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllBucketsUseCase {
    suspend operator fun invoke(): Flow<Buckets> {
        return flow { }
    }
}
