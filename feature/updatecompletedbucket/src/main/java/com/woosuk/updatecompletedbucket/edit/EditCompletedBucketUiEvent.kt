package com.woosuk.updatecompletedbucket.edit

sealed interface EditCompletedBucketUiEvent {
    data class EditSuccess(val bucketId:Int) : EditCompletedBucketUiEvent
}
