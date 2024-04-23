package com.woosuk.datastore

import androidx.datastore.core.DataStore
import com.woosuk.datastore.SettingPreferences.DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
import com.woosuk.datastore.SettingPreferences.DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
import com.woosuk.datastore.SettingPreferences.DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
import com.woosuk.datastore.SettingPreferences.DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED
import com.woosuk.datastore.SettingPreferences.DarkThemeConfigProto.UNRECOGNIZED
import com.woosuk.domain.model.DarkThemeConfig
import com.woosuk.domain.model.SettingInfo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WoosukPreferencesDataSource @Inject constructor(
    private val settingPreferences: DataStore<SettingPreferences>,
) {
    val settingInfo = settingPreferences.data.map {
        SettingInfo(
            darkThemeConfig = when (it.darkThemeConfig) {
                null,
                DARK_THEME_CONFIG_UNSPECIFIED,
                UNRECOGNIZED,
                DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                -> DarkThemeConfig.FOLLOW_SYSTEM

                DARK_THEME_CONFIG_LIGHT -> DarkThemeConfig.LIGHT
                DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
            },
        )
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        settingPreferences.updateData {
            it.copy {
                this.darkThemeConfig = when (darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM -> DARK_THEME_CONFIG_FOLLOW_SYSTEM
                    DarkThemeConfig.LIGHT -> DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DARK_THEME_CONFIG_DARK
                }
            }
        }
    }
}
