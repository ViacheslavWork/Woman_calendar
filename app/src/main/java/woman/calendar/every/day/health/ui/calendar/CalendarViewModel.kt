package woman.calendar.every.day.health.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.usecase.GetMonthUseCase
import woman.calendar.every.day.health.domain.usecase.UpdatePeriodDayUseCase

private const val monthsCashSize = 8L

class CalendarViewModel(
    private val getMonthUseCase: GetMonthUseCase,
    private val updatePeriodDayUseCase: UpdatePeriodDayUseCase,
) : ViewModel() {
    private val _months = MutableLiveData<List<ItemMonth>>()
    val months: LiveData<List<ItemMonth>> = _months
    private lateinit var prevMonth: LocalDate
    private var countOfMounts = monthsCashSize

    init {
        viewModelScope.launch { fillInitialData() }
    }

    fun getPrevMonth() {
        viewModelScope.launch {
            months.value
                ?.toMutableList()
                ?.let {
                    countOfMounts += monthsCashSize
                    for (i in 0..monthsCashSize) {
                        prevMonth = prevMonth.minusMonths(1)
                        it.add(
                            0,
                            ItemMonth(
                                date = prevMonth,
                                days = getMonthUseCase.execute(prevMonth)
                                    .map { day -> ItemDay.fromDay(day) })
                        )
                    }
                    _months.postValue(it)
                }
        }
    }

    private suspend fun fillInitialData() {
        val date = LocalDate.now().minusMonths(countOfMounts)
        prevMonth = date
        val months = mutableListOf<ItemMonth>()
        for (i in 0..countOfMounts + 1) {
            val dateTemp = date.plusMonths(i)
            months.add(
                ItemMonth(
                    date = dateTemp,
                    days = getMonthUseCase.execute(dateTemp)
                        .map { day -> ItemDay.fromDay(day) })
            )
        }
        _months.postValue(months)
    }

    fun handleEvent(event: CalendarEvent) {
        when (event) {
            is CalendarEvent.OnDayClick -> {
                viewModelScope.launch {
                    updatePeriodDayUseCase.execute(event.day)
                    fillInitialData()
                }
            }
        }
    }
}