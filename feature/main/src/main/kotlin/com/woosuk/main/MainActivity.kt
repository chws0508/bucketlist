package com.woosuk.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.woosuk.theme.BucketlistTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // We handle all the insets manually
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            BucketlistTheme {
                BucketListApp()
            }
        }
    }
}
