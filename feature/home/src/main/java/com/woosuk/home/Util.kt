package com.woosuk.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.woosuk.domain.model.AgeRange
import com.woosuk.domain.model.AgeRange.Fifties
import com.woosuk.domain.model.AgeRange.Forties
import com.woosuk.domain.model.AgeRange.OldAge
import com.woosuk.domain.model.AgeRange.Thirties
import com.woosuk.domain.model.AgeRange.Twenties
import com.woosuk.domain.model.AgeRange.UnSpecified
import com.woosuk.domain.model.BucketCategory
import com.woosuk.domain.model.BucketCategory.Health
import com.woosuk.domain.model.BucketCategory.Learning
import com.woosuk.domain.model.BucketCategory.Travel
import com.woosuk.domain.model.BucketCategory.Unspecified
import com.woosuk.domain.model.BucketCategory.Work

object BucketUiUtil {
    @Composable
    fun getCategoryName(bucketCategory: BucketCategory) = when (bucketCategory) {
        Travel -> stringResource(id = R.string.travel)
        Health -> stringResource(id = R.string.health)
        Work -> stringResource(id = R.string.work)
        Learning -> stringResource(id = R.string.learning)
        Unspecified -> stringResource(id = R.string.unspecified)
    }

    @Composable
    fun getAgeName(ageRange: AgeRange) = when (ageRange) {
        Twenties -> stringResource(R.string.twenties)
        Thirties -> stringResource(R.string.thirties)
        Forties -> stringResource(R.string.forties)
        Fifties -> stringResource(R.string.fifties)
        OldAge -> stringResource(R.string.oldage)
        UnSpecified -> stringResource(id = R.string.unspecified)
    }
}
