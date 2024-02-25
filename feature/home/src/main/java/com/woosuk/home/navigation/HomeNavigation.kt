package com.woosuk.home.navigation

import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.woosuk.home.HomeRoute

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHomeRoute(navOptions: NavOptions? = null) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(
    onClickEditBucket: () -> Unit,
    onClickCompleteBucket: (id:Int) -> Unit,
    topPaddingDp: Dp,
) {
    composable(
        route = HOME_ROUTE,
    ) {
        HomeRoute(
            onEditBucketClick = onClickEditBucket,
            onBucketCompleteClick = onClickCompleteBucket,
            topPaddingDp = topPaddingDp,
        )
    }
}
