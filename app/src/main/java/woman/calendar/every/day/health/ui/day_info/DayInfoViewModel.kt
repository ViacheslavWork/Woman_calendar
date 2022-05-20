package woman.calendar.every.day.health.ui.day_info

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

class DayInfoViewModel(
    private val getDayUseCase: GetDayUseCase,
    private val getCycleUseCase: GetCycleUseCase
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
}