package com.woosuk.home

import androidx.annotation.StringRes
import com.woosuk.domain.model.Buckets

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Error : HomeUiState
    data class Success(val buckets: Buckets) : HomeUiState
}

sealed class ViewMode(@StringRes val nameId: Int) {
    data object AgeRange : ViewMode(nameId = R.string.age_range_view_mode)
    data object Category : ViewMode(nameId = R.string.category_view_mode)
}
