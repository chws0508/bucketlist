package com.woosuk.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.woosuk.add.navigation.addBucketScreen
import com.woosuk.add.navigation.navigateToAddRoute
import com.woosuk.addcompletebucket.navigation.addCompleteBucketScreen
import com.woosuk.addcompletebucket.navigation.navigateToAddCompleteBucket
import com.woosuk.completebucket.navigation.completeBucketScreen
import com.woosuk.completebucket.navigation.navigateToCompleteBucket
import com.woosuk.completedbucketdetail.navigation.completedBucketDetailScreen
import com.woosuk.completedbucketdetail.navigation.navigateToCompletedBucketDetail
import com.woosuk.home.navigation.HOME_ROUTE
import com.woosuk.home.navigation.homeScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BucketListApp(
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_ROUTE,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    fun showSnackBar(message: String) = scope.launch { snackbarHostState.showSnackbar(message) }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (currentDestination?.route == HOME_ROUTE) {
                MainTopBar(scrollBehavior = scrollBehavior)
            }
        },
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
                    containerColor = MaterialTheme.colorScheme.primary,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Home Add",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
            ) {
                homeScreen(
                    onClickEditBucket = { navController.navigateToAddRoute(null) },
                    onClickCompleteBucket = { id ->
                        navController.navigateToAddCompleteBucket(
                            null,
                            id,
                        )
                    },
                    topPaddingDp = innerPadding.calculateTopPadding(),
                )
                addBucketScreen(
                    onBackClick = navController::popBackStack,
                    onShowSnackBar = ::showSnackBar,
                )
                completeBucketScreen()
                addCompleteBucketScreen(
                    onBackClick = navController::popBackStack,
                    onShowSnackBar = ::showSnackBar,
                    onNavigateToCompletedBucketDetail = navController::navigateToCompletedBucketDetail,
                )
                completedBucketDetailScreen(
                    onNavigateToCompletedList = navController::navigateToCompleteBucket,
                    onShowSnackBar = ::showSnackBar,
                )
            }
        }
    }
}
