package com.natural.cycles.period.tracker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import com.natural.cycles.period.tracker.domain.model.Cycle
import com.natural.cycles.period.tracker.domain.usecase.cycles.GetLastCyclesUseCase
import com.natural.cycles.period.tracker.domain.usecase.periods.GetMinCountOfPeriodsUseCase
import com.natural.cycles.period.tracker.domain.usecase.days.GetDayUseCase
import com.natural.cycles.period.tracker.domain.usecase.days.GetWeekUseCase
import com.natural.cycles.period.tracker.domain.usecase.firebase.days.DownloadDaysFromFirebaseUseCase
import kotlinx.coroutines.flow.collectLatest


class HomeViewModel(
    private val getWeekUseCase: GetWeekUseCase,
    private val getMinCountOfPeriodsUseCase: GetMinCountOfPeriodsUseCase,
    private val getLastCyclesUseCase: GetLastCyclesUseCase,
    private val getDayUseCase: GetDayUseCase,
    private val downloadDaysFromFirebaseUseCase: DownloadDaysFromFirebaseUseCase
) : ViewModel() {
    private val _weekDays = MutableLiveData<List<ItemDayOfWeek>>()
    val weekDays: LiveData<List<ItemDayOfWeek>> = _weekDays

    private val _countOfPeriods = MutableLiveData<Int>()
    val countOfPeriods: LiveData<Int> = _countOfPeriods

    private val _lastCycles = MutableLiveData<List<Cycle>>()
    val lastCycles: LiveData<List<Cycle>> = _lastCycles

    private val _symptoms = MutableLiveData<List<HomeSymptomItem>>()
    val symptoms: LiveData<List<HomeSymptomItem>> = _symptoms

    private val _isDaysFromFirebaseDownloaded = MutableLiveData<Boolean>()
    val isDaysFromFirebaseDownloaded: LiveData<Boolean> = _isDaysFromFirebaseDownloaded

    init {
        updateUI()
    }

    fun updateUI() {
        updateSymptoms()
        updateWeek()
        updateCountOfPeriods()
        updateLastCycles()
    }

    fun downloadDaysFromFirebase() {
        viewModelScope.launch {
            downloadDaysFromFirebaseUseCase.execute().collectLatest {
                it?.let { _isDaysFromFirebaseDownloaded.postValue(it) }
            }
        }
    }

    private fun updateSymptoms() {
        viewModelScope.launch {
            _symptoms.postValue(
                getDayUseCase.execute(LocalDate.now()).symptoms
                    .map { HomeSymptomItem.fromSymptom(it) }
                    .sortedBy { it.symptomType }
                    .toList()
            )
        }
    }

    private fun updateWeek() {
        viewModelScope.launch {
            _weekDays.postValue(
                getWeekUseCase.execute(LocalDate.now()).map { ItemDayOfWeek.fromDay(it) })
        }
    }

    private fun updateCountOfPeriods() {
        viewModelScope.launch {
            _countOfPeriods.postValue(getMinCountOfPeriodsUseCase.execute())
        }
    }

    private fun updateLastCycles() {
        viewModelScope.launch {
            _lastCycles.postValue(getLastCyclesUseCase.execute(3))
        }
    }
}