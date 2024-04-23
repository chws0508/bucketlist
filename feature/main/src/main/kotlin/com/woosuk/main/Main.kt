package com.woosuk.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.woosuk.add.navigation.addBucketScreen
import com.woosuk.add.navigation.navigateToAddRoute
import com.woosuk.completedbucket.navigation.COMPLETED_BUCKET_LIST_ROUTE
import com.woosuk.completedbucket.navigation.completeBucketScreen
import com.woosuk.completedbucketdetail.navigation.COMPLETED_BUCKET_ROUTE_WITH_ARGUMENT
import com.woosuk.completedbucketdetail.navigation.completedBucketDetailScreen
import com.woosuk.completedbucketdetail.navigation.navigateToCompletedBucketDetail
import com.woosuk.home.navigation.HOME_ROUTE
import com.woosuk.home.navigation.homeScreen
import com.woosuk.setting.navigation.settingScreen
import com.woosuk.theme.WoosukTheme
import com.woosuk.updatecompletedbucket.navigation.addCompletedBucketScreen
import com.woosuk.updatecompletedbucket.navigation.editCompletedBucketScreen
import com.woosuk.updatecompletedbucket.navigation.navigateToAddCompletedBucket
import com.woosuk.updatecompletedbucket.navigation.navigateToEditCompletedBucket
import kotlinx.coroutines.launch

@Composable
fun BucketListApp(
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_ROUTE,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    fun showSnackBar(message: String) = scope.launch { snackbarHostState.showSnackbar(message) }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        bottomBar = {
            if (BottomTab.isCurrentScreenBottomTab(currentDestination?.route)) {
                MainBottomNavigationBar(
                    currentRoute = currentDestination?.route ?: return@Scaffold,
                    navController = navController,
                )
            }
        },
        floatingActionButton = {
            if (currentDestination?.route == BottomTab.HomeTab.route) {
                FloatingActionButton(
                    onClick = { navController.navigateToAddRoute(null) },
                    containerColor = WoosukTheme.colors.tossBlue3,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Home Add",
                        tint = WoosukTheme.colors.systemWhite,
                    )
                }
            }
        },
        containerColor = WoosukTheme.colors.grayScale1,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
            ) {
                homeScreen(
                    onClickCompleteBucket = { id ->
                        navController.navigateToAddCompletedBucket(
                            null,
                            id,
                        )
                    },
                    onNavigateToCompletedBucketDetail = navController::navigateToCompletedBucketDetail,
                )
                addBucketScreen(
                    onBackClick = navController::popBackStack,
                    onShowSnackBar = ::showSnackBar,
                )
                completeBucketScreen(
                    onNavigateToEditCompletedBucket = navController::navigateToEditCompletedBucket,
                    onNavigateToCompletedBucketDetail = navController::navigateToCompletedBucketDetail,
                )
                addCompletedBucketScreen(
                    onBackClick = navController::popBackStack,
                    onShowSnackBar = ::showSnackBar,
                    onNavigateToCompletedBucketDetail = { bucketId ->
                        navController.navigateToCompletedBucketDetail(
                            bucketId = bucketId,
                            navOptions = navOptions {
                                popUpTo(
                                    navController.currentBackStackEntry?.destination?.route
                                        ?: return@navOptions,
                                ) {
                                    inclusive = true
                                }
                                launchSingleTop
                            },
                        )
                    },
                )
                completedBucketDetailScreen(
                    onNavigateToEditScreen = navController::navigateToEditCompletedBucket,
                    onShowSnackBar = ::showSnackBar,
                    onBackClick = navController::popBackStack,
                )
                editCompletedBucketScreen(
                    onBackClick = navController::popBackStack,
                    onNavigateToCompletedBucketDetail = { bucketId ->
                        navController.navigateToCompletedBucketDetail(
                            navOptions = navOptions {
                                if(navController.previousBackStackEntry?.destination?.route == COMPLETED_BUCKET_ROUTE_WITH_ARGUMENT){
                                    popUpTo(COMPLETED_BUCKET_ROUTE_WITH_ARGUMENT) {
                                        inclusive = true
                                    }
                                } else {
                                    popUpTo(COMPLETED_BUCKET_LIST_ROUTE) {
                                        inclusive = false
                                    }
                                }
                                launchSingleTop = true
                            },
                            bucketId = bucketId,
                        )
                    },
                    onShowSnackBar = ::showSnackBar,
                )
                settingScreen()
            }
        }
    }
}
