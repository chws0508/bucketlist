package com.woosuk.data.repository

import com.woosuk.datastore.WoosukPreferencesDataSource
import com.woosuk.domain.model.DarkThemeConfig
import com.woosuk.domain.model.SettingInfo
import com.woosuk.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultSettingRepository @Inject constructor(
    private val woosukPreferencesDataSource: WoosukPreferencesDataSource,
) : SettingRepository {
    override val settingInfo: Flow<SettingInfo> = woosukPreferencesDataSource.settingInfo

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        woosukPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
    }
}
