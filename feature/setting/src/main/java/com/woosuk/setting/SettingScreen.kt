package com.woosuk.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.woosuk.domain.model.DarkThemeConfig
import com.woosuk.domain.model.SettingInfo
import com.woosuk.theme.WoosukTheme
import com.woosuk.theme.defaultFontFamily
import ui.noRippleClickable

@Composable
fun SettingBucketRoute(
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val settingInfo by viewModel.settingInfo.collectAsStateWithLifecycle()

    SettingScreen(
        settingInfo = settingInfo,
        onClickDarkThemeConfig = viewModel::updateDarkThemeConfig,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    settingInfo: SettingInfo,
    onClickDarkThemeConfig: (DarkThemeConfig) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.setting),
                    fontFamily = defaultFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = WoosukTheme.colors.systemBlack,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = WoosukTheme.colors.grayScale1,
            ),
        )
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            AppearanceSection(
                currentDarkThemeConfig = settingInfo.darkThemeConfig,
                onClickDarkThemeConfig = onClickDarkThemeConfig,
            )
            Spacer(modifier = Modifier.height(16.dp))
            AppAboutSection()
        }
    }
}

@Composable
fun AppearanceSection(
    modifier: Modifier = Modifier,
    currentDarkThemeConfig: DarkThemeConfig,
    onClickDarkThemeConfig: (DarkThemeConfig) -> Unit = {},
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.appearance),
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = WoosukTheme.colors.tossBlue3,
        )
        Spacer(modifier = Modifier.height(16.dp))
        for (darkThemeConfig in DarkThemeConfig.entries) {
            val text = when (darkThemeConfig) {
                DarkThemeConfig.FOLLOW_SYSTEM -> stringResource(R.string.system_default)
                DarkThemeConfig.LIGHT -> stringResource(R.string.light)
                DarkThemeConfig.DARK -> stringResource(R.string.dark)
            }
            val iconTint =
                if (darkThemeConfig == currentDarkThemeConfig) WoosukTheme.colors.tossBlue3
                else WoosukTheme.colors.grayScale2

            Row(
                modifier = Modifier
                    .noRippleClickable {
                        onClickDarkThemeConfig(darkThemeConfig)
                    }
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = text,
                    fontFamily = defaultFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = WoosukTheme.colors.systemBlack,
                )
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.check),
                    tint = iconTint,
                )
            }
        }
    }
}

@Composable
fun AppAboutSection(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.about_app),
            fontFamily = defaultFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = WoosukTheme.colors.tossBlue3,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .noRippleClickable { context.openPrivacyTermUrl() }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = modifier,
                text = stringResource(R.string.privacy_terms),
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = WoosukTheme.colors.systemBlack,
            )
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Right",
                tint = WoosukTheme.colors.systemBlack,
            )
        }
    }
}

private fun Context.openPrivacyTermUrl() {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(PRIVACY_TERM_URL))
    startActivity(intent)
}

private const val PRIVACY_TERM_URL = "https://sites.google.com/view/woosuk-bucket-list"

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
private fun SettingScreenPreview() {
    WoosukTheme {
        SettingScreen(settingInfo = SettingInfo(DarkThemeConfig.DARK))
    }
}
