package com.woosuk.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosuk.domain.model.Bucket
import com.woosuk.domain.usecase.DeleteBucketUseCase
import com.woosuk.domain.usecase.GetAllBucketsUseCase
import com.woosuk.domain.usecase.UpdateBucketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllBucketsUseCase: GetAllBucketsUseCase,
    private val deleteBucketUseCase: DeleteBucketUseCase,
    private val updateBucketUseCase: UpdateBucketUseCase,
) : ViewModel() {

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val homeUiState = _homeUiState.asStateFlow()

    init {
        loadBuckets()
    }

    private fun loadBuckets() {
        viewModelScope.launch {
            getAllBucketsUseCase().onEach { buckets ->
                _homeUiState.value = HomeUiState.Success(buckets)
            }.launchIn(this)
        }
    }

    fun deleteBucket(bucket: Bucket) {
        viewModelScope.launch {
            deleteBucketUseCase(bucket)
        }
    }

    fun updateBucket(bucket: Bucket) {
        viewModelScope.launch {
            updateBucketUseCase(bucket = bucket)
        }
    }
}
