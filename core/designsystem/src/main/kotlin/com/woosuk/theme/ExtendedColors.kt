package com.woosuk.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtendedColors(
    val coolGray6: Color = CoolGray6,
    val coolGray5: Color = CoolGray5,
    val coolGray4: Color = CoolGray4,
    val coolGray3: Color = CoolGray3,
    val coolGray2: Color = CoolGray2,
    val coolGray1: Color = CoolGray1,
    val coolGray0: Color = CoolGray0,

    val warmGray0: Color = WarmGray0,
    val warmGray1: Color = WarmGray1,
    val warmGray2: Color = WarmGray2,
    val warmGray3: Color = WarmGray3,
    val warmGray4: Color = WarmGray4,
    val warmGray5: Color = WarmGray5,
    val warmGray6: Color = WarmGray6,

    val grayScale0: Color = GrayScale0,
    val grayScale1: Color = GrayScale1,
    val grayScale2: Color = GrayScale2,
    val grayScale3: Color = GrayScale3,

    val tossBlue0: Color = TossBlue0,
    val tossBlue1: Color = TossBlue1,
    val tossBlue2: Color = TossBlue2,
    val tossBlue3: Color = TossBlue3,
    val tossBlue4: Color = TossBlue4,
    val tossBlue5: Color = TossBlue5,

    val tossGreen:Color = TossGreen,
    val tossRed:Color = TossRed,
)

val LocalExtendedColors = staticCompositionLocalOf { ExtendedColors() }

val MaterialTheme.extendedColor: ExtendedColors
    @Composable
    @ReadOnlyComposable
    get() = LocalExtendedColors.current

