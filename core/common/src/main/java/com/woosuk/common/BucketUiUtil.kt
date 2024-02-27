package com.woosuk.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.BucketCategory

object BucketUiUtil {
    @Composable
    fun getCategoryName(bucketCategory: BucketCategory) = when (bucketCategory) {
        BucketCategory.Travel -> stringResource(id = R.string.travel)
        BucketCategory.Health -> stringResource(id = R.string.health)
        BucketCategory.Work -> stringResource(id = R.string.work)
        BucketCategory.Learning -> stringResource(id = R.string.learning)
        BucketCategory.Unspecified -> stringResource(id = R.string.unspecified)
    }

    @Composable
    fun getAgeName(ageRange: AgeRange) = when (ageRange) {
        AgeRange.Twenties -> stringResource(R.string.twenties)
        AgeRange.Thirties -> stringResource(R.string.thirties)
        AgeRange.Forties -> stringResource(R.string.forties)
        AgeRange.Fifties -> stringResource(R.string.fifties)
        AgeRange.OldAge -> stringResource(R.string.oldage)
        AgeRange.UnSpecified -> stringResource(id = R.string.unspecified)
    }

    fun getCategoryStringResourceId(bucketCategory: BucketCategory) = when (bucketCategory) {
        BucketCategory.Travel -> R.string.travel
        BucketCategory.Health -> R.string.health
        BucketCategory.Work -> R.string.work
        BucketCategory.Learning -> R.string.learning
        BucketCategory.Unspecified -> R.string.unspecified
    }

    fun getAgeNameStringResourceId(ageRange: AgeRange) = when (ageRange) {
        AgeRange.Twenties -> R.string.twenties
        AgeRange.Thirties -> R.string.thirties
        AgeRange.Forties -> R.string.forties
        AgeRange.Fifties -> R.string.fifties
        AgeRange.OldAge -> R.string.oldage
        AgeRange.UnSpecified -> R.string.unspecified
    }
}
