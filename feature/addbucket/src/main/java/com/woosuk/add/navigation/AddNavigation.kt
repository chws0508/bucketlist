package com.woosuk.add.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.woosuk.add.AddBucketRoute

const val ADD_ROUTE = "add_route"

fun NavController.navigateToAddRoute(navOptions: NavOptions?) = navigate(ADD_ROUTE, navOptions)

fun NavGraphBuilder.addScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = ADD_ROUTE,
    ) {
        AddBucketRoute(onBackClick = onBackClick)
    }
}
