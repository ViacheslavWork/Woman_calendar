package woman.calendar.every.day.health.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.Cycle
import woman.calendar.every.day.health.domain.model.Day
import woman.calendar.every.day.health.domain.usecase.cycles.GetCycleUseCase
import woman.calendar.every.day.health.domain.usecase.days.GetDayUseCase
import woman.calendar.every.day.health.domain.usecase.days.SaveDayUseCase

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