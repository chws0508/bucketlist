package com.woosuk.completedbucketdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosuk.completedbucketdetail.CompletedBucketDetailUiEvent.DeleteSuccess
import com.woosuk.completedbucketdetail.navigation.COMPLETED_BUCKET_ID_ARGUMENT
import com.woosuk.domain.model.CompletedBucket
import com.woosuk.domain.usecase.DeleteCompleteBucketUseCase
import com.woosuk.domain.usecase.GetCompletedBucketUseCase
import com.woosuk.domain.usecase.UpdateCompletedBucketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CompletedBucketDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val deleteCompletedBucketUseCase: DeleteCompleteBucketUseCase,
    private val getCompletedBucketUseCase: GetCompletedBucketUseCase,
) : ViewModel() {
    private val bucketId: Int = checkNotNull(savedStateHandle[COMPLETED_BUCKET_ID_ARGUMENT])

    private val _completedBucket: MutableStateFlow<CompletedBucket?> = MutableStateFlow(null)
    val completedBucket = _completedBucket.asStateFlow()

    private val _uiEvent: MutableSharedFlow<CompletedBucketDetailUiEvent> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _completedBucket.value = getCompletedBucketUseCase(bucketId)
        }
    }

    fun deleteCompletedBucket() {
        viewModelScope.launch {
            completedBucket.value?.let {
                deleteCompletedBucketUseCase(it)
                _uiEvent.emit(DeleteSuccess)
            }
        }
    }
}
