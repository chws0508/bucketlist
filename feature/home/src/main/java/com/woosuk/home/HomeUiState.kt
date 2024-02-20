package com.woosuk.home

import com.woosuk.domain.model.Buckets

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Error : HomeUiState
    data class Success(val buckets: Buckets) : HomeUiState
}
