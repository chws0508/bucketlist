package com.woosuk.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val LocalCustomColors: ProvidableCompositionLocal<WoosukColor> =
    staticCompositionLocalOf {
        error("No Color Provied")
    }

@Composable
fun WoosukTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> darkColor
        else -> lightColor
    }
    CompositionLocalProvider(
        LocalCustomColors provides colorScheme,
        content = content,
    )
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.grayScale1.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
}

object WoosukTheme {
    val colors: WoosukColor
        @Composable
        get() = LocalCustomColors.current
}
