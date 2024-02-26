package com.woosuk.completedbucketdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.woosuk.completedbucketdetail.CompletedBucketDetailRoute

const val COMPLETED_BUCKET_DETAIL_ROUTE = "complete_bucket_route"
const val COMPLETED_BUCKET_ID_ARGUMENT = "bucket_id"
const val COMPLETED_BUCKET_ROUTE_WITH_ARGUMENT =
    "$COMPLETED_BUCKET_DETAIL_ROUTE/{$COMPLETED_BUCKET_ID_ARGUMENT}"

fun NavController.navigateToCompletedBucketDetail(
    bucketId: Int,
    navOptions: NavOptions? = null,
) =
    navigate(
        route = "$COMPLETED_BUCKET_DETAIL_ROUTE/$bucketId", navOptions = navOptions,
    )

fun NavGraphBuilder.completedBucketDetailScreen(
    onNavigateToEditScreen: (bucketId:Int) -> Unit,
    onShowSnackBar: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(
        route = COMPLETED_BUCKET_ROUTE_WITH_ARGUMENT,
        arguments = listOf(navArgument(COMPLETED_BUCKET_ID_ARGUMENT) { type = NavType.IntType }),
    ) {
        CompletedBucketDetailRoute(
            onNavigateToEditScreen = onNavigateToEditScreen,
            onShowSnackBar = onShowSnackBar,
            onBackClick = onBackClick,
        )
    }
}
