package com.period.tracker.natural.cycles.ui.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticlesViewModel : ViewModel() {
    private val _tabs = MutableLiveData<ArticlesTab>()
    val tabs: LiveData<ArticlesTab> = _tabs

    private val _containerResume = MutableLiveData<Unit>()
    val containerResume: LiveData<Unit> = _containerResume

    fun onContainerResume() {
        _containerResume.postValue(Unit)
    }
}