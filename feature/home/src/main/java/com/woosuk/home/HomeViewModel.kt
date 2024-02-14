package com.woosuk.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _topAppBarColorCondition: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val topAppBarColorCondition = _topAppBarColorCondition.asStateFlow()

    fun toggleTopBarColor() {
        _topAppBarColorCondition.value = !topAppBarColorCondition.value
    }
}
