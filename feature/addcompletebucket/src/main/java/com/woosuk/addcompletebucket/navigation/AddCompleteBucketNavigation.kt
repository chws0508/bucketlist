package com.woosuk.addcompletebucket.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.woosuk.addcompletebucket.AddCompleteBucketRoute

const val ADD_COMPLETE_BUCKET_ROUTE = "add_complete_bucket_route"

fun NavController.navigateToAddCompleteBucket(navOptions: NavOptions?) = navigate(
    ADD_COMPLETE_BUCKET_ROUTE,
    navOptions,
)

fun NavGraphBuilder.addCompleteBucketScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = ADD_COMPLETE_BUCKET_ROUTE,
    ) {
        AddCompleteBucketRoute(onBackClick = onBackClick)
    }
}
