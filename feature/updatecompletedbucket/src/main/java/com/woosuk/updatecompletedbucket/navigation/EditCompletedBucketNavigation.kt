package com.woosuk.updatecompletedbucket.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.woosuk.updatecompletedbucket.edit.EditCompletedBucketRoute

const val EDIT_COMPLETE_BUCKET_ROUTE = "edit_complete_bucket_route"
const val EDIT_COMPLETE_BUCKET_ROUTE_WITH_ARGUMENT =
    "$EDIT_COMPLETE_BUCKET_ROUTE/{$BUCKET_ID_ARGUMENT}"

fun NavController.navigateToEditCompletedBucket(navOptions: NavOptions?, bucketId: Int) = navigate(
    "$EDIT_COMPLETE_BUCKET_ROUTE/$bucketId",
    navOptions,
)

fun NavGraphBuilder.editCompletedBucketScreen(
    onBackClick: () -> Unit,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    composable(
        route = EDIT_COMPLETE_BUCKET_ROUTE_WITH_ARGUMENT,
        arguments = listOf(navArgument(BUCKET_ID_ARGUMENT) { type = NavType.IntType }),
    ) {
        EditCompletedBucketRoute(
            onBackClick = onBackClick,
            onShowSnackBar = onShowSnackBar,
            onNavigateToCompletedBucketDetail = onNavigateToCompletedBucketDetail
        )
    }
}
