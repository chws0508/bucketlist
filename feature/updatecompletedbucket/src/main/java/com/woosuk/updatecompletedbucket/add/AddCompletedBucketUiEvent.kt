package com.woosuk.updatecompletedbucket.add

sealed interface AddCompletedBucketUiEvent {
    data class AddAddCompletedEvent(val bucketId: Int) : AddCompletedBucketUiEvent
}
