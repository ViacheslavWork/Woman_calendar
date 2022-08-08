package com.natural.cycles.period.tracker.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import com.natural.cycles.period.tracker.domain.model.Cycle
import com.natural.cycles.period.tracker.domain.model.Day
import com.natural.cycles.period.tracker.domain.usecase.cycles.GetCycleUseCase
import com.natural.cycles.period.tracker.domain.usecase.days.GetDayUseCase
import com.natural.cycles.period.tracker.domain.usecase.days.SaveDayUseCase

class NotesViewModel(
    private val getDayUseCase: GetDayUseCase,
    private val getCycleUseCase: GetCycleUseCase,
    private val saveDayUseCase: SaveDayUseCase
) : ViewModel() {
    private val _day = MutableLiveData<Day>()
    val day: LiveData<Day> = _day
    private val _cycle = MutableLiveData<Cycle?>()
    val cycle: LiveData<Cycle?> = _cycle

    fun loadDay(date: LocalDate) {
        viewModelScope.launch {
            _day.postValue(getDayUseCase.execute(date))
        }
        viewModelScope.launch {
            _cycle.postValue(getCycleUseCase.execute(date))
        }
    }

    fun saveNote(note: String) {
        viewModelScope.launch {
            day.value?.let { saveDayUseCase.execute(it.apply { notes = note }) }
        }
    }
}