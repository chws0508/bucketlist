package com.woosuk.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.woosuk.theme.WoosukTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // We handle all the insets manually
        enableEdgeToEdge()

        setContent {
            WoosukTheme {
                BucketListApp()
            }
        }
    }
}
