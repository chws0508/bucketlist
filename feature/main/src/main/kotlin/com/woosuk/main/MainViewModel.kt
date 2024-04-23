package com.woosuk.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woosuk.domain.model.DarkThemeConfig
import com.woosuk.domain.model.SettingInfo
import com.woosuk.domain.usecase.GetSettingInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getSettingInfoUseCase: GetSettingInfoUseCase,
) : ViewModel() {
    val settingInfo = getSettingInfoUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        SettingInfo(DarkThemeConfig.FOLLOW_SYSTEM),
    )
}
