package com.woosuk.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.woosuk.designsystem.R

val defaultFontFamily =
    FontFamily(
        Font(
            resId = R.font.grotesk_400,
            weight = FontWeight.Normal,
        ),
        Font(
            resId = R.font.grotesk_light_300,
            weight = FontWeight.Light,
        ),
        Font(
            resId = R.font.grotesk_smb_600,
            weight = FontWeight.Bold,
        ),
        Font(
            resId = R.font.grotesk_medium_500,
            weight = FontWeight.Medium,
        ),
        Font(
            resId = R.font.grotesk_bold_700,
            weight = FontWeight.ExtraBold,
        ),
    )

// Set of Material typography styles to start with
val Typography =
    Typography(
        bodyLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
        /* Other default text styles to override
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
         */
    )
