package com.woosuk.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.BucketCategory
import com.woosuk.domain.usecase.AddBucketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddBucketViewModel @Inject constructor(
    private val addBucketUseCase: AddBucketUseCase,
) : ViewModel() {

    private val _addBucketUiState: MutableStateFlow<AddBucketUiState> = MutableStateFlow(
        AddBucketUiState(),
    )
    val addBucketUiState = _addBucketUiState.asStateFlow()

    private val _addBucketUIEvent: MutableSharedFlow<AddBucketUiEvent> = MutableSharedFlow()
    val addBucketUiEvent = _addBucketUIEvent.asSharedFlow()

    fun onBucketTitleChanged(inputTitle: String) {
        _addBucketUiState.update { it.copy(title = inputTitle) }
    }

    fun onBucketAgeRangeChanged(inputAgeRange: AgeRange) {
        _addBucketUiState.update { it.copy(ageRange = inputAgeRange) }
    }

    fun onBucketCategoryChanged(inputBucketCategory: BucketCategory) {
        _addBucketUiState.update { it.copy(category = inputBucketCategory) }
    }

    fun onBucketDescriptionChanged(inputDescription: String) {
        _addBucketUiState.update { it.copy(description = inputDescription) }
    }

    fun addBucket(
        createdAt: LocalDateTime = LocalDateTime.now(),
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            addBucketUseCase(
                title = addBucketUiState.value.title,
                ageRange = addBucketUiState.value.ageRange ?: return@launch,
                bucketCategory = addBucketUiState.value.category ?: return@launch,
                description = addBucketUiState.value.description,
                createdAt = createdAt,
            )
            _addBucketUIEvent.emit(AddBucketUiEvent.AddCompleteEvent)
        }
    }
}
