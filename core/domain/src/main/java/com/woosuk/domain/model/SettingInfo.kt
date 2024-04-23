package com.woosuk.domain.model

data class SettingInfo(
    val darkThemeConfig: DarkThemeConfig,
)

enum class DarkThemeConfig {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK,
}
