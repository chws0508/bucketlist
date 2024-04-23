package com.woosuk.domain.usecase

import com.woosuk.domain.model.SettingInfo
import com.woosuk.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingInfoUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
) {
    operator fun invoke(): Flow<SettingInfo> = settingRepository.settingInfo
}
