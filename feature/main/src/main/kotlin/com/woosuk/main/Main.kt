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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.woosuk.add.navigation.addScreen
import com.woosuk.add.navigation.navigateToAddRoute
import com.woosuk.home.navigation.HOME_ROUTE
import com.woosuk.home.navigation.homeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BucketListApp(
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_ROUTE,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (BottomTab.isCurrentScreenBottomTab(currentDestination?.route)) {
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
            if (BottomTab.isCurrentScreenBottomTab(currentDestination?.route)) {
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
                    navigateToEditBucketScreen = { navController.navigateToAddRoute(null) },
                    topPaddingDp = innerPadding.calculateTopPadding(),
                )
                addScreen(onBackClick = navController::popBackStack)
            }
        }
    }
}
