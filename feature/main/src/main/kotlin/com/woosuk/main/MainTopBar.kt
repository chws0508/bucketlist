package com.woosuk.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.woosuk.home.R
import com.woosuk.theme.BucketlistTheme
import com.woosuk.theme.defaultFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String = stringResource(id = R.string.app_name),
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val containerColor =
        when (val state = scrollBehavior.state.overlappedFraction) {
            in 1f..Float.MAX_VALUE -> MaterialTheme.colorScheme.onPrimary
            else -> MaterialTheme.colorScheme.onPrimary.copy(alpha = state / 10)
        }
    TopAppBar(
        modifier = Modifier.background(Color.Transparent),
        title = {
            Text(
                text = title,
                fontFamily = defaultFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = containerColor,
                titleContentColor = MaterialTheme.colorScheme.tertiary,
            ),
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainTopBarPreview() {
    BucketlistTheme {
        Box {
            MainTopBar(scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior())
        }
    }
}
