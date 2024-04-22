package com.woosuk.completedbucket.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.woosuk.completedbucket.CompleteBucketRoute

const val COMPLETED_BUCKET_LIST_ROUTE = "completed_bucket_list_route"

fun NavController.navigateToCompleteBucket(navOptions: NavOptions? = null) =
    navigate(COMPLETED_BUCKET_LIST_ROUTE, navOptions)

fun NavGraphBuilder.completeBucketScreen(
    onNavigateToEditCompletedBucket: (bucketId: Int) -> Unit,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
) {
    composable(
        route = COMPLETED_BUCKET_LIST_ROUTE,
    ) {
        CompleteBucketRoute(
            onNavigateToEditCompletedBucket = onNavigateToEditCompletedBucket,
            onNavigateToCompletedBucketDetail = onNavigateToCompletedBucketDetail,
        )
    }
}
