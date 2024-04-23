package com.woosuk.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosuk.domain.model.DarkThemeConfig
import com.woosuk.domain.model.SettingInfo
import com.woosuk.domain.usecase.GetSettingInfoUseCase
import com.woosuk.domain.usecase.SetDarkThemeConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    getSettingInfoUseCase: GetSettingInfoUseCase,
    private val setDarkThemeConfigUseCase: SetDarkThemeConfigUseCase,
) : ViewModel() {
    val settingInfo = getSettingInfoUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        SettingInfo(darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM),
    )

    fun updateDarkThemeConfig(
        darkThemeConfig: DarkThemeConfig,
    ) {
        viewModelScope.launch { setDarkThemeConfigUseCase(darkThemeConfig) }
    }
}
