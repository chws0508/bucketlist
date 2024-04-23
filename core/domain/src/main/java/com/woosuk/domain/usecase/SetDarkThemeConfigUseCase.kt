package com.woosuk.domain.usecase

import com.woosuk.domain.model.DarkThemeConfig
import com.woosuk.domain.repository.SettingRepository
import javax.inject.Inject

class SetDarkThemeConfigUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
) {
    suspend operator fun invoke(darkThemeConfig: DarkThemeConfig) {
        settingRepository.setDarkThemeConfig(darkThemeConfig)
    }
}
