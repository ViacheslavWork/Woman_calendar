package woman.calendar.every.day.health.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.threeten.bp.LocalDate
import timber.log.Timber
import woman.calendar.every.day.health.domain.model.StateOfDay
import woman.calendar.every.day.health.domain.usecase.GetMonthUseCase
import woman.calendar.every.day.health.domain.usecase.MarkPeriodDayUseCase
import woman.calendar.every.day.health.domain.usecase.UnmarkPeriodDayUseCase

private const val monthsCashSize = 8L

class CalendarViewModel(
    private val getMonthUseCase: GetMonthUseCase,
    private val markPeriodDayUseCase: MarkPeriodDayUseCase,
    private val unmarkPeriodDayUseCase: UnmarkPeriodDayUseCase
) : ViewModel() {
    private val _months = MutableLiveData<List<ItemMonth>>()
    val months: LiveData<List<ItemMonth>> = _months
    private lateinit var prevMonth: LocalDate

    init {
        fillInitialData()
    }

    fun getPrevMonth() {
        months.value
            ?.toMutableList()
            ?.let {
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

    private fun fillInitialData() {
        val date = LocalDate.now().minusMonths(monthsCashSize)
        prevMonth = date
        val months = mutableListOf<ItemMonth>()
        for (i in 0..monthsCashSize + 1) {
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
                if (event.day.stateOfDay == StateOfDay.PERIOD) {
                    markPeriodDayUseCase.execute(event.day.date)
                } else {
                    unmarkPeriodDayUseCase.execute(event.day.date)
                }
                fillInitialData()
            }
        }
    }
}