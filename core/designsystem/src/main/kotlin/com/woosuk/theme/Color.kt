package com.woosuk.theme
import androidx.compose.ui.graphics.Color

val SystemWhite = Color(0xffffffff)
val SystemBlack = Color(0xff000000)

val TossBlue0 = Color(0xFFDDEBFC)
val TossBlue1 = Color(0xFFC2D3E4)
val TossBlue2 = Color(0xFF4593FC)
val TossBlue3 = Color(0xFF3182F6)
val TossBlue4 = Color(0xFF3D6AFE)
val TossBlue5 = Color(0xFF1B64DA)


val CoolGray6 = Color(0xFF283447)
val CoolGray5 = Color(0xFF333D4B)
val CoolGray4 = Color(0xFF4E5968)
val CoolGray3 = Color(0xFF6B7684)
val CoolGray2 = Color(0xFF8B95A1)
val CoolGray1 = Color(0xFFB0B7C1)
val CoolGray0 = Color(0xFFD2D8DD)

val GrayScale0 = Color(0xFFFFFFFF)
val GrayScale1 = Color(0xFFF2F2F5)
val GrayScale2 = Color(0xFFE2E4E6)
val GrayScale3 = Color(0xFF9BA0A9)

val TossGreen = Color(0xFF1CD88A)
val TossRed = Color(0xFFFE2E01)

data class WoosukColor(
    val systemWhite: Color,
    val systemBlack: Color,
    val tossBlue0: Color,
    val tossBlue1: Color,
    val tossBlue2: Color,
    val tossBlue3: Color,
    val tossBlue4: Color,
    val tossBlue5: Color,
    val coolGray0: Color,
    val coolGray1: Color,
    val coolGray2: Color,
    val coolGray3: Color,
    val coolGray4: Color,
    val coolGray5: Color,
    val coolGray6: Color,
    val grayScale0: Color,
    val grayScale1: Color,
    val grayScale2: Color,
    val grayScale3: Color,
    val tossGreen: Color,
    val tossRed: Color,
)

val lightColor = WoosukColor(
    systemWhite = SystemWhite,
    systemBlack = SystemBlack,
    tossBlue0 = TossBlue0,
    tossBlue1 = TossBlue1,
    tossBlue2 = TossBlue2,
    tossBlue3 = TossBlue3,
    tossBlue4 = TossBlue4,
    tossBlue5 = TossBlue5,
    coolGray0 = CoolGray0,
    coolGray1 = CoolGray1,
    coolGray2 = CoolGray2,
    coolGray3 = CoolGray3,
    coolGray4 = CoolGray4,
    coolGray5 = CoolGray5,
    coolGray6 = CoolGray6,
    grayScale0 = GrayScale0,
    grayScale1 = GrayScale1,
    grayScale2 = GrayScale2,
    grayScale3 = GrayScale3,
    tossGreen = TossGreen,
    tossRed = TossRed,
)
val darkColor = WoosukColor(
    systemWhite = CoolGray6,
    systemBlack = SystemWhite,
    tossBlue0 = TossBlue0,
    tossBlue1 = TossBlue1,
    tossBlue2 = TossBlue2,
    tossBlue3 = TossBlue3,
    tossBlue4 = TossBlue4,
    tossBlue5 = TossBlue5,
    coolGray0 = CoolGray0,
    coolGray1 = CoolGray1,
    coolGray2 = CoolGray2,
    coolGray3 = SystemWhite,
    coolGray4 = CoolGray4,
    coolGray5 = CoolGray5,
    coolGray6 = CoolGray6,
    grayScale0 = GrayScale0,
    grayScale1 = CoolGray5,
    grayScale2 = GrayScale2,
    grayScale3 = GrayScale3,
    tossGreen = TossGreen,
    tossRed = TossRed,
)
