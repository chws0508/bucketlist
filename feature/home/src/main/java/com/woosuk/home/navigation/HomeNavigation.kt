package com.woosuk.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.woosuk.home.HomeRoute

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHomeRoute(navOptions: NavOptions? = null) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(
    onClickCompleteBucket: (id: Int) -> Unit,
    onNavigateToCompletedBucketDetail: (bucketId: Int) -> Unit,
) {
    composable(
        route = HOME_ROUTE,
    ) {
        HomeRoute(
            onBucketCompleteClick = onClickCompleteBucket,
            onNavigateToCompletedBucketDetail = onNavigateToCompletedBucketDetail,
        )
    }
}
