package com.woosuk.add

sealed interface AddBucketUiEvent {
    data object AddCompleteEvent : AddBucketUiEvent
}
