package com.woosuk.updatecompletedbucket.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosuk.domain.model.CompletedBucket
import com.woosuk.domain.usecase.GetCompletedBucketUseCase
import com.woosuk.domain.usecase.UpdateCompletedBucketUseCase
import com.woosuk.updatecompletedbucket.navigation.BUCKET_ID_ARGUMENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EditCompletedBucketViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCompletedBucketUseCase: GetCompletedBucketUseCase,
    private val updateCompletedBucketUseCase: UpdateCompletedBucketUseCase,
) : ViewModel() {

    private val bucketId: Int =
        checkNotNull(savedStateHandle[BUCKET_ID_ARGUMENT]) { "bucketID를 전달받지 못했어요" }

    private val _uiState: MutableStateFlow<CompletedBucket?> = MutableStateFlow(null)
    val uiState: StateFlow<CompletedBucket?> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<EditCompletedBucketUiEvent> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _uiState.value = getCompletedBucketUseCase(bucketId)
        }
    }

    fun onDiaryChanged(inputDiary: String) {
        _uiState.update { it?.copy(description = inputDiary) }
    }

    fun addImageUris(inputImageUris: List<String>) {
        _uiState.update { it?.copy(imageUrls = it.imageUrls + inputImageUris) }
    }

    fun deleteImage(position: Int) {
        _uiState.update {
            it?.copy(imageUrls = it.imageUrls.filterIndexed { index, _ -> index != position })
        }
    }

    fun onCompletedDateChanged(inputDate: LocalDateTime) {
        _uiState.update { it?.copy(completedAt = inputDate) }
    }

    fun updateCompletedBucket() {
        viewModelScope.launch {
            uiState.value?.let {
                updateCompletedBucketUseCase(it)
                _uiEvent.emit(EditCompletedBucketUiEvent.EditSuccess(it.bucket.id))
            }
        }
    }
}
