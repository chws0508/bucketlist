package com.woosuk.updatecompletedbucket.add

import com.woosuk.domain.model.Bucket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

data class CompletedBucketUiState(
    val bucket: Bucket,
    val imageUris: List<String> = emptyList(),
    val diary: String = "",
    val completedDate: LocalDateTime = LocalDateTime.now(),
)

fun CompletedBucketUiState.NewUiState(
    bucket: Bucket = this.bucket,
    imageUris: List<String> = this.imageUris,
    diary: String = this.diary,
    completedDate: LocalDateTime = this.completedDate,
) = copy(
    bucket = bucket, imageUris = imageUris, diary = diary, completedDate = completedDate,
)

fun MutableStateFlow<CompletedBucketUiState?>.updateTo(
    block: (CompletedBucketUiState).() -> CompletedBucketUiState,
) {
    update { it?.block() }
}
