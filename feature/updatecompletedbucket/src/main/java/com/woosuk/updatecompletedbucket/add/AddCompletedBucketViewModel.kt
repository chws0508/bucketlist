package com.woosuk.updatecompletedbucket.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosuk.updatecompletedbucket.add.AddCompletedBucketUiEvent.AddAddCompletedEvent
import com.woosuk.updatecompletedbucket.navigation.BUCKET_ID_ARGUMENT
import com.woosuk.domain.usecase.AddCompletedBucketUseCase
import com.woosuk.domain.usecase.GetBucketUseCase
import com.woosuk.domain.usecase.UpdateBucketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddCompletedBucketViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBucketUseCase: GetBucketUseCase,
    private val updateBucketUseCase: UpdateBucketUseCase,
    private val addCompletedBucketUseCase: AddCompletedBucketUseCase,
) : ViewModel() {

    private val bucketId: Int =
        checkNotNull(savedStateHandle[BUCKET_ID_ARGUMENT]) { "bucketID를 전달받지 못했어요" }

    private val _uiState: MutableStateFlow<CompletedBucketUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<CompletedBucketUiState?> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<AddCompletedBucketUiEvent> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            val bucket = checkNotNull(getBucketUseCase(bucketId)) { "해당 bucket이 존재하지 않아요" }
            _uiState.value = CompletedBucketUiState(bucket)
        }
    }

    fun onDiaryChanged(inputDiary: String) {
        _uiState.updateTo { NewUiState(diary = inputDiary) }
    }

    fun addImageUris(inputImageUris: List<String>) {
        _uiState.updateTo { NewUiState(imageUris = imageUris + inputImageUris) }
    }

    fun deleteImage(position: Int) {
        _uiState.updateTo {
            val newImageUris = imageUris.filterIndexed { index, _ -> index != position }
            NewUiState(imageUris = newImageUris)
        }
    }

    fun onCompletedDateChanged(inputDate: LocalDateTime) {
        _uiState.updateTo { NewUiState(completedDate = inputDate) }
    }

    fun addCompletedBucket(completionDate: LocalDateTime = LocalDateTime.now()) {
        viewModelScope.launch {
            uiState.value?.let {
                updateBucketUseCase(it.bucket.copy(isCompleted = true))
                addCompletedBucketUseCase(
                    bucketId = it.bucket.id,
                    completedDate = completionDate,
                    imageUris = it.imageUris,
                    diary = it.diary,
                )
                _uiEvent.emit(AddAddCompletedEvent(it.bucket.id))
            }
        }
    }
}

