package com.woosuk.domain.model

import java.time.LocalDateTime

data class CompletedBucket(
    val bucket: Bucket,
    val completedAt: LocalDateTime,
    val imageUrls: List<String>,
    val description: String?,
) {
    init {
        require(bucket.isCompleted) { SHOULD_IS_COMPLETED_STATE_TRUE }
    }

    companion object {
        private const val SHOULD_IS_COMPLETED_STATE_TRUE =
            " Bucket의 완료 상태는 true여야 합니다!!"
    }
}
