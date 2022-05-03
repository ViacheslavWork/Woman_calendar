package woman.calendar.every.day.health.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.Cycle
import woman.calendar.every.day.health.domain.usecase.cycles.GetLastCyclesUseCase
import woman.calendar.every.day.health.domain.usecase.periods.GetCountOfPeriodsUseCase
import woman.calendar.every.day.health.domain.usecase.days.GetDayUseCase
import woman.calendar.every.day.health.domain.usecase.days.GetWeekUseCase


class HomeViewModel(
    private val getWeekUseCase: GetWeekUseCase,
    private val getCountOfPeriodsUseCase: GetCountOfPeriodsUseCase,
    private val getLastCyclesUseCase: GetLastCyclesUseCase,
    private val getDayUseCase: GetDayUseCase
) : ViewModel() {
    private val _weekDays = MutableLiveData<List<ItemDayOfWeek>>()
    val weekDays: LiveData<List<ItemDayOfWeek>> = _weekDays

    private val _countOfPeriods = MutableLiveData<Int>()
    val countOfPeriods: LiveData<Int> = _countOfPeriods

    private val _lastCycles = MutableLiveData<List<Cycle>>()
    val lastCycles: LiveData<List<Cycle>> = _lastCycles

    private val _symptoms = MutableLiveData<List<HomeSymptomItem>>()
    val symptoms: LiveData<List<HomeSymptomItem>> = _symptoms

    init {
        updateUI()
    }

    fun updateUI() {
        updateSymptoms()
        updateWeek()
        updateCountOfPeriods()
        updateLastCycles()
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
            _countOfPeriods.postValue(getCountOfPeriodsUseCase.execute())
        }
    }

    private fun updateLastCycles() {
        viewModelScope.launch {
            _lastCycles.postValue(getLastCyclesUseCase.execute(3))
        }
    }
}