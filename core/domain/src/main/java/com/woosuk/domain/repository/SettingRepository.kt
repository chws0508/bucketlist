package com.woosuk.domain.repository

import com.woosuk.domain.model.DarkThemeConfig
import com.woosuk.domain.model.SettingInfo
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    val settingInfo: Flow<SettingInfo>

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)
}
