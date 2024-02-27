package com.woosuk.completedbucket

import androidx.annotation.StringRes
import com.woosuk.common.BucketUiUtil
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.BucketCategory
import com.woosuk.domain.model.CompletedBucket

data class CompletedBucketsUiState(
    val selectedCategory: SelectableCategory,
    val completedBuckets: List<CompletedBucket>,
)

sealed class SelectableCategory(@StringRes val stringResourceId: Int) {
    data object All : SelectableCategory(R.string.all_category)

    data class BucketCategoryType(val bucketCategory: BucketCategory) :
        SelectableCategory(BucketUiUtil.getCategoryStringResourceId(bucketCategory))

    data class AgeRangeType(val ageRange: AgeRange) :
        SelectableCategory(BucketUiUtil.getAgeNameStringResourceId(ageRange))
}
