package com.woosuk.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.woosuk.completebucket.navigation.COMPLETE_BUCKET_ROUTE
import com.woosuk.completebucket.navigation.navigateToCompleteBucket
import com.woosuk.home.navigation.HOME_ROUTE
import com.woosuk.home.navigation.navigateToHomeRoute
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.defaultFontFamily
import com.woosuk.theme.extendedColor

@Composable
fun MainBottomNavigationBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
    navController: NavController,
) {
    NavigationBar(
        modifier = modifier.clip(RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp)),
        containerColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        BottomTab.values().forEach { tab ->
            NavigationBarItem(
                label = {
                    Text(
                        text = tab.title,
                        fontFamily = defaultFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                    )
                },
                selected = currentRoute == tab.route,
                onClick = { navController.navigateToTabScreen(tab) },
                icon = {
                    Icon(
                        imageVector = tab.iconImageVector,
                        contentDescription = tab.title,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(),
                    )
                },
                colors =
                    NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.onPrimary,
                        selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                        selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                        unselectedIconColor = MaterialTheme.extendedColor.warmGray2,
                        unselectedTextColor = MaterialTheme.extendedColor.warmGray2,
                    ),
            )
        }
    }
}

fun NavController.navigateToTabScreen(bottomTab: BottomTab) {
    val tabNavOptions =
        navOptions {
            popUpTo(HOME_ROUTE) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

    when (bottomTab) {
        BottomTab.HomeTab -> navigateToHomeRoute(tabNavOptions)
        BottomTab.CompleteBucketRoute -> navigateToCompleteBucket(tabNavOptions)
    }
}

@Preview
@Composable
fun MainBottomNavigationBarPreview() {
    BucketlistTheme {
        val navController = rememberNavController()
        MainBottomNavigationBar(
            currentRoute = HOME_ROUTE,
            navController = navController,
        )
    }
}

enum class BottomTab(
    val route: String,
    val iconImageVector: ImageVector,
    val title: String,
) {
    HomeTab(HOME_ROUTE, Icons.Filled.Home, "홈"),
    CompleteBucketRoute(COMPLETE_BUCKET_ROUTE, Icons.Filled.List, "달성기록"),
    ;

    companion object {
        fun isCurrentScreenBottomTab(currentRoute: String?): Boolean =
            entries.map { it.route }.contains(currentRoute)
    }
}
