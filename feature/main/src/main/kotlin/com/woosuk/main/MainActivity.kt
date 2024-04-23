package com.woosuk.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.woosuk.domain.model.DarkThemeConfig
import com.woosuk.theme.WoosukTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // We handle all the insets manually
        enableEdgeToEdge()

        setContent {
            val settingInfo by viewModel.settingInfo.collectAsStateWithLifecycle()
            WoosukTheme(
                shouldUseSystemDefault = settingInfo.darkThemeConfig == DarkThemeConfig.FOLLOW_SYSTEM,
                darkTheme = settingInfo.darkThemeConfig == DarkThemeConfig.DARK,
            ) {
                BucketListApp()
            }
        }
    }
}
