package com.woosuk.add

import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.BucketCategory

data class UpdateBucketUiState(
    val title: String = "",
    val ageRange: AgeRange? = null,
    val category: BucketCategory? = null,
    val description: String = "",
) {
    val canAddBucket = title != "" && ageRange != null && category != null
}
