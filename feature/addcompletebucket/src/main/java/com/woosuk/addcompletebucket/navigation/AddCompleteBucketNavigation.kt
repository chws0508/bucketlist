package com.woosuk.addcompletebucket.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.woosuk.addcompletebucket.AddCompleteBucketRoute

const val BUCKET_ID_ARGUMENT = "bucket_id"
const val ADD_COMPLETE_BUCKET_ROUTE = "add_complete_bucket_route"
const val ADD_COMPLETE_BUCKET_ROUTE_WITH_ARGUMENT =
    "$ADD_COMPLETE_BUCKET_ROUTE/{$BUCKET_ID_ARGUMENT}"

fun NavController.navigateToAddCompleteBucket(navOptions: NavOptions?, bucketId: Int) = navigate(
    "$ADD_COMPLETE_BUCKET_ROUTE/$bucketId",
    navOptions,
)

fun NavGraphBuilder.addCompleteBucketScreen(
    onBackClick: () -> Unit,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    composable(
        route = ADD_COMPLETE_BUCKET_ROUTE_WITH_ARGUMENT,
        arguments = listOf(navArgument(BUCKET_ID_ARGUMENT) { type = NavType.IntType }),
    ) {
        AddCompleteBucketRoute(
            onBackClick = onBackClick,
            onShowSnackBar = onShowSnackBar,
            onNavigateToCompletedBucketDetail = onNavigateToCompletedBucketDetail
        )
    }
}
