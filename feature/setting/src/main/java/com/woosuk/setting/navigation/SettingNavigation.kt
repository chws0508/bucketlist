package com.woosuk.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.woosuk.setting.SettingBucketRoute

const val SETTING_ROUTE = "setting_route"

fun NavController.navigateToSetting(
    navOptions: NavOptions? = null,
) = navigate(SETTING_ROUTE, navOptions)

fun NavGraphBuilder.settingScreen() {
    composable(
        route = SETTING_ROUTE,
    ) {
        SettingBucketRoute()
    }
}
