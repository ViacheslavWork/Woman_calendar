package com.period.tracker.natural.cycles.ui.onboarding.container

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class OnBoardingContainerViewModel : ViewModel() {
    val nextClickFlow = MutableSharedFlow<Unit>()
    fun onNextFragmentClick() {
        viewModelScope.launch {
            nextClickFlow.emit(Unit)
        }
    }
}