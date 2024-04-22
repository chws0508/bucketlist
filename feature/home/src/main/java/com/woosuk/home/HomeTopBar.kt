package com.woosuk.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woosuk.theme.WoosukTheme
import com.woosuk.theme.defaultFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    title: String = stringResource(id = R.string.app_name),
    scrollBehavior: TopAppBarScrollBehavior,
    onOptionClick: () -> Unit,
) {
    val containerColor =
        when (val state = scrollBehavior.state.overlappedFraction) {
            in 1f..Float.MAX_VALUE -> WoosukTheme.colors.systemWhite
            else -> WoosukTheme.colors.systemWhite.copy(alpha = state / 10)
        }
    TopAppBar(
        title = {
            Text(
                text = title,
                fontFamily = defaultFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = WoosukTheme.colors.systemBlack,
            )
        },
        colors =
        TopAppBarDefaults.topAppBarColors(
            titleContentColor = WoosukTheme.colors.systemBlack,
            containerColor = containerColor,
            scrolledContainerColor = containerColor,
        ),
        scrollBehavior = scrollBehavior,
        actions = {
            Text(
                modifier = Modifier
                    .clickable {
                        onOptionClick()
                    }
                    .padding(end = 16.dp),
                text = stringResource(R.string.topbar_sorting_option_),
                fontSize = 16.sp,
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
                color = WoosukTheme.colors.tossBlue2,
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainTopBarPreview() {
    WoosukTheme {
        Box {
            HomeTopBar(scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()) {

            }
        }
    }
}


