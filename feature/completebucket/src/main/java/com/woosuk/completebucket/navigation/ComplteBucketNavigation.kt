package com.woosuk.completebucket.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.woosuk.completebucket.CompleteBucketRoute

const val COMPLETE_BUCKET_ROUTE = "complete_bucket_route"

fun NavController.navigateToCompleteBucket(navOptions: NavOptions? = null) =
    navigate(COMPLETE_BUCKET_ROUTE, navOptions)

fun NavGraphBuilder.completeBucketScreen() {
    composable(
        route = COMPLETE_BUCKET_ROUTE,
    ) {
        CompleteBucketRoute()
    }
}
