package com.woosuk.completedbucket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.BucketCategory
import com.woosuk.domain.model.CompletedBucket
import com.woosuk.domain.usecase.DeleteCompletedBucketUseCase
import com.woosuk.domain.usecase.GetAllCompletedBucketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletedBucketViewModel @Inject constructor(
    getAllCompletedBucketUseCase: GetAllCompletedBucketUseCase,
    private val deleteCompletedBucketUseCase: DeleteCompletedBucketUseCase,
) : ViewModel() {

    private val allCompletedBuckets = getAllCompletedBucketUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList(),
        )

    val allSelectableCategories =
        listOf(SelectableCategory.All) +
            BucketCategory.entries.map { SelectableCategory.BucketCategoryType(it) } +
            AgeRange.entries.map { SelectableCategory.AgeRangeType(it) }

    private val _selectedCategory: MutableStateFlow<SelectableCategory> =
        MutableStateFlow(SelectableCategory.All)
    private val selectedCategory = _selectedCategory.asStateFlow()

    val completeBucketsUiState =
        combine(allCompletedBuckets, selectedCategory) { completedBuckets, selectedCategory ->
            when (selectedCategory) {
                is SelectableCategory.All -> CompletedBucketsUiState(
                    selectedCategory,
                    completedBuckets,
                )

                is SelectableCategory.AgeRangeType -> CompletedBucketsUiState(
                    selectedCategory,
                    completedBuckets.filter { it.bucket.ageRange == selectedCategory.ageRange },
                )

                is SelectableCategory.BucketCategoryType -> CompletedBucketsUiState(
                    selectedCategory,
                    completedBuckets.filter { it.bucket.category == selectedCategory.bucketCategory },
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = CompletedBucketsUiState(
                selectedCategory = SelectableCategory.All,
                completedBuckets = listOf(),
            ),
        )

    fun onSelectedCategoryChanged(inputCategory: SelectableCategory) {
        _selectedCategory.value = inputCategory
    }

    fun deleteCompletedBucket(completedBucket: CompletedBucket) {
        viewModelScope.launch { deleteCompletedBucketUseCase(completedBucket) }
    }
}
