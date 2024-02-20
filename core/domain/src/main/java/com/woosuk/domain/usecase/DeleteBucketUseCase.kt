package com.woosuk.domain.usecase

import com.woosuk.domain.model.Bucket

class DeleteBucketUseCase {
    suspend operator fun invoke(bucket: Bucket) {}
}
