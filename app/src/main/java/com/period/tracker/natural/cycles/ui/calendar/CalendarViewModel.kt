package com.period.tracker.natural.cycles.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import timber.log.Timber
import com.period.tracker.natural.cycles.domain.usecase.RecalculateFromDayUseCase
import com.period.tracker.natural.cycles.domain.usecase.days.GetMonthUseCase
import com.period.tracker.natural.cycles.domain.usecase.notification.OnOffEverydayNotificationUseCase
import com.period.tracker.natural.cycles.domain.usecase.periods.GetMinCountOfPeriodsUseCase
import com.period.tracker.natural.cycles.domain.usecase.periods.MarkDayUseCase
import com.period.tracker.natural.cycles.utils.LocalDateHelper

private const val monthsCashSize = 12L

class CalendarViewModel(
    private val getMonthUseCase: GetMonthUseCase,
    private val markDayUseCase: MarkDayUseCase,
    private val recalculateFromDayUseCase: RecalculateFromDayUseCase,
    private val onOffEverydayNotificationUseCase: OnOffEverydayNotificationUseCase,
    private val getMinCountOfPeriodsUseCase: GetMinCountOfPeriodsUseCase
) : ViewModel() {
    private val _months = MutableLiveData<List<ItemMonth>>()
    val months: LiveData<List<ItemMonth>> = _months
    private val _countOfPeriods = MutableLiveData<Int>()
    val countOfPeriods: LiveData<Int> = _countOfPeriods
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
                                date = LocalDateHelper.getByMonth(prevMonth.year, prevMonth.month),
                                days = getMonthUseCase.execute(prevMonth)
                                    .map { day -> ItemDay.fromDay(day) })
                        )
                    }
                    it.forEach { Timber.d(it.date.toString()) }
                    _months.value = it
                }
        }
    }

    private suspend fun fillInitialData() {
        val start = System.currentTimeMillis()
        _countOfPeriods.postValue(getMinCountOfPeriodsUseCase.execute())
        val date = LocalDate.now().minusMonths(countOfMounts)
        prevMonth = date
        val months = mutableListOf<ItemMonth>()
        for (i in 0..countOfMounts + 1) {
            val dateTemp = date.plusMonths(i)
            months.add(
                ItemMonth(
                    date = LocalDateHelper.getByMonth(dateTemp.year, dateTemp.month),
                    days = getMonthUseCase.execute(dateTemp)
                        .map { day -> ItemDay.fromDay(day) })
            )
        }
        Timber.d("fillInitialData time: ${System.currentTimeMillis() - start}")
        _months.postValue(months)
    }

    fun handleEvent(event: CalendarEvent) {
        when (event) {
            is CalendarEvent.OnDayClick -> {
                viewModelScope.launch {
                    markDayUseCase.execute(event.day)
                    recalculateFromDayUseCase.execute(event.day.date)
                    fillInitialData()
                    onOffEverydayNotificationUseCase.execute()
                }
            }
        }
    }
}