package com.natural.cycles.period.tracker.domain.usecase.periods

import com.natural.cycles.period.tracker.domain.model.Day
import com.natural.cycles.period.tracker.domain.model.StateOfDay.EXPECTED_NEW_PERIOD
import com.natural.cycles.period.tracker.domain.model.StateOfDay.PERIOD
import com.natural.cycles.period.tracker.domain.usecase.days.GetDayUseCase
import com.natural.cycles.period.tracker.domain.usecase.days.SaveDayUseCase
import com.natural.cycles.period.tracker.preferences.EarliestPeriodPreferences
import com.natural.cycles.period.tracker.preferences.LatestPeriodPreferences
import com.natural.cycles.period.tracker.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import timber.log.Timber

class MarkDayUseCase(
    private val saveDayUseCase: SaveDayUseCase,
    private val getDayUseCase: GetDayUseCase,
    private val getMinCountOfPeriodsUseCase: GetMinCountOfPeriodsUseCase,
    private val earliestPeriodPreferences: EarliestPeriodPreferences,
    private val latestPeriodPreferences: LatestPeriodPreferences,
    private val getLastPeriodsUseCase: GetLastPeriodsUseCase
) {
    suspend fun execute(day: Day) = withContext(Dispatchers.IO) {
//        Timber.d(getEarliestPeriodUseCase.execute().toString())
        if (day.stateOfDay == PERIOD) {
            if (latestPeriodPreferences.getEnd() == null
                || day.date.isAfter(latestPeriodPreferences.getEnd())
            ) {
                latestPeriodPreferences.setEnd(day.date)
            }
            if (getDayUseCase.execute(day.date).stateOfDay == EXPECTED_NEW_PERIOD) {
                overrideExpectedDays(day)
                return@withContext
            }
            if (getMinCountOfPeriodsUseCase.execute() < Constants.MIN_COUNT_PERIODS_FOR_INSIGHT
                || earliestPeriodPreferences.getStart() == null
                || day.date.isBefore(earliestPeriodPreferences.getStart())
            ) {
                earliestPeriodPreferences.setStart(day.date)
                for (i in 0 until Constants.STANDARD_LENGTH_OF_PERIOD) {
                    launch {
                        saveDayUseCase.execute(
                            getDayUseCase
                                .execute(day.date.plusDays(i.toLong()))
                                .apply { stateOfDay = day.stateOfDay })
                        if (latestPeriodPreferences.getEnd() == null
                            || day.date.plusDays(i.toLong())
                                .isAfter(latestPeriodPreferences.getEnd())
                        ) {
                            latestPeriodPreferences.setEnd(day.date)
                        }
                    }
                }
            } else {
                launch {
                    saveDayUseCase.execute(
                        getDayUseCase.execute(day.date).apply { stateOfDay = day.stateOfDay })
                }
                launch { findPeriodDaysAfter(day) }
            }
            launch { findPeriodDaysBefore(day) }
            return@withContext
        }
        saveDayUseCase.execute(
            getDayUseCase.execute(day.date).apply { stateOfDay = day.stateOfDay })
        if (latestPeriodPreferences.getEnd() == day.date) {
            if (getLastPeriodsUseCase.execute(1).isNotEmpty()) {
                latestPeriodPreferences.setEnd(getLastPeriodsUseCase.execute(1)[0].finish)
            }
            Timber.d(latestPeriodPreferences.getEnd().toString())
        }
    }

    private suspend fun findPeriodDaysBefore(day: Day) {
        val tempDates = mutableListOf<LocalDate>()
        var tempDate = day.date
        var dayCounter = 1
        while (dayCounter < Constants.NUMBER_OF_DAYS_TO_SEARCH_NEAREST_PERIOD) {
            tempDate = tempDate.minusDays(1)
            tempDates.add(tempDate)
            dayCounter++
            if (getDayUseCase.execute(tempDate).stateOfDay == PERIOD) {
                tempDates.forEach {
                    saveDayUseCase.execute(
                        getDayUseCase.execute(it).apply { stateOfDay = PERIOD })
                }
                return
            }
        }
    }

    private suspend fun findPeriodDaysAfter(day: Day) {
        val tempDates = mutableListOf<LocalDate>()
        var tempDate = day.date
        var dayCounter = 1
        while (dayCounter < Constants.NUMBER_OF_DAYS_TO_SEARCH_NEAREST_PERIOD) {
            tempDate = tempDate.plusDays(1)
            tempDates.add(tempDate)
            dayCounter++
            if (getDayUseCase.execute(tempDate).stateOfDay == PERIOD) {
                tempDates.forEach {
                    saveDayUseCase.execute(
                        getDayUseCase.execute(it).apply { stateOfDay = PERIOD })
                }
                return
            }
        }
    }

    private suspend fun overrideExpectedDays(day: Day) {
        var tempDate = day.date
        while (getDayUseCase.execute(tempDate.minusDays(1)).stateOfDay == EXPECTED_NEW_PERIOD) {
            tempDate = tempDate.minusDays(1)
        }
        while (getDayUseCase.execute(tempDate).stateOfDay == EXPECTED_NEW_PERIOD) {
            saveDayUseCase.execute(getDayUseCase.execute(tempDate).apply { stateOfDay = PERIOD })
            tempDate = tempDate.plusDays(1)
        }
    }
}