package com.woosuk.completedbucketdetail

sealed interface CompletedBucketDetailUiEvent {
    data object DeleteSuccess : CompletedBucketDetailUiEvent
}
