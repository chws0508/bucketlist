package com.woosuk.updatecompletedbucket.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.woosuk.updatecompletedbucket.add.AddCompletedBucketRoute

const val BUCKET_ID_ARGUMENT = "bucket_id"
const val ADD_COMPLETE_BUCKET_ROUTE = "add_complete_bucket_route"
const val ADD_COMPLETE_BUCKET_ROUTE_WITH_ARGUMENT =
    "$ADD_COMPLETE_BUCKET_ROUTE/{$BUCKET_ID_ARGUMENT}"

fun NavController.navigateToAddCompletedBucket(navOptions: NavOptions?, bucketId: Int) = navigate(
    "$ADD_COMPLETE_BUCKET_ROUTE/$bucketId",
    navOptions,
)

fun NavGraphBuilder.addCompletedBucketScreen(
    onBackClick: () -> Unit,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    composable(
        route = ADD_COMPLETE_BUCKET_ROUTE_WITH_ARGUMENT,
        arguments = listOf(navArgument(BUCKET_ID_ARGUMENT) { type = NavType.IntType }),
    ) {
        AddCompletedBucketRoute(
            onBackClick = onBackClick,
            onShowSnackBar = onShowSnackBar,
            onNavigateToCompletedBucketDetail = onNavigateToCompletedBucketDetail
        )
    }
}
