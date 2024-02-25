package com.woosuk.addcompletebucket

sealed interface CompletedBucketUiEvent {
    data class AddCompletedEvent(val bucketId: Int) : CompletedBucketUiEvent
}
