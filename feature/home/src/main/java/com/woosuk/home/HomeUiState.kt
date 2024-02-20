package com.woosuk.home

import com.woosuk.domain.model.Buckets

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Error(val message: String?) : HomeUiState
    data class Success(val buckets: Buckets) : HomeUiState
}
