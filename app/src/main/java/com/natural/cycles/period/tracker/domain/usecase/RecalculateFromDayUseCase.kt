package com.natural.cycles.period.tracker.domain.usecase

import com.natural.cycles.period.tracker.domain.model.Interval
import com.natural.cycles.period.tracker.domain.model.Period
import com.natural.cycles.period.tracker.domain.model.StateOfDay
import com.natural.cycles.period.tracker.domain.model.StateOfDay.*
import com.natural.cycles.period.tracker.domain.usecase.days.GetDayUseCase
import com.natural.cycles.period.tracker.domain.usecase.days.SaveDayUseCase
import com.natural.cycles.period.tracker.preferences.LatestPeriodPreferences
import com.natural.cycles.period.tracker.utils.Constants
import com.natural.cycles.period.tracker.utils.Constants.MIN_COUNT_PERIODS_FOR_INSIGHT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import timber.log.Timber

private const val MAX_COUNT_MONTHS_FOR_INSIGHT = 10L

class RecalculateFromDayUseCase(
    private val saveDayUseCase: SaveDayUseCase,
    private val getDayUseCase: GetDayUseCase,
    private val latestPeriodPreferences: LatestPeriodPreferences
) {
    suspend fun execute(date: LocalDate) = withContext(Dispatchers.IO) {
        val startTime = System.currentTimeMillis()
        val averagePeriodLength = async { calculateAveragePeriod() ?: 1L }
        val averageInterval = async { calculateAverageInterval() }
        var initialDate: LocalDate? = findNearestPeriod(date)
        clearExpectedPeriodDaysBeforeDate(date)

        while (initialDate != null) {
//            val startTime = System.currentTimeMillis()
            initialDate =
                calculateCycle(initialDate, averagePeriodLength.await(), averageInterval.await())
            Timber.d("Calculate period time: ${System.currentTimeMillis() - startTime}")
        }
    }

    private suspend fun clearExpectedPeriodDaysBeforeDate(date: LocalDate) {
        var tempDate = date.minusDays(1)
        while (getDayUseCase.execute(tempDate).stateOfDay != PERIOD
            && tempDate.isAfter(date.minusMonths(2))
        ) {
            val day = getDayUseCase.execute(tempDate)
            if (day.stateOfDay == EXPECTED_NEW_PERIOD) {
                saveDayUseCase.execute(day.apply { stateOfDay = null })
            }
            tempDate = tempDate.minusDays(1)
        }
    }

    private suspend fun calculateCycle(
        dateInPeriod: LocalDate,
        averagePeriodLength: Long,
        averageInterval: Long?
    ): LocalDate? = withContext(Dispatchers.IO) {
//        val startOfPeriod = getStartOfPeriod(dateInPeriod)

        val endOfPeriod = async { getFinishOfPeriod(dateInPeriod) }
        val nextPeriodDate = getNextPeriodDate(endOfPeriod.await().plusDays(1))

        launch {
            val endDelayAfterPeriod =
                setDelayAfterPeriod(start = endOfPeriod.await().plusDays(1))
            val endFertileDays = setFertileDays(start = endDelayAfterPeriod.plusDays(1))
            val ovulationDay = setOvulationDay(endOfPeriod.await())
            clearStatusDaysUntilNextPeriod(start = endFertileDays.plusDays(1))
            if (latestPeriodPreferences.getEnd()
                    ?.let { !endOfPeriod.await().isBefore(it) } == true
            ) {
                launch {
                    setExpectedPeriodDays(
                        averagePeriodLength,
                        averageInterval,
                        endOfPeriod.await()
                    )
                }
            }
        }


//        Timber.d("Start of period: $startOfPeriod")
//        Timber.d("Finish of period: $finishOfPeriod")
//        Timber.d("finishDelayAfterPeriod: $finishDelayAfterPeriod")
//        Timber.d("finishFertileDays: $finishFertileDays")
//        Timber.d("averagePeriodLength: $averagePeriodLength")
//        Timber.d("averageInterval: $averageInterval")
//        Timber.d("ovulationDay: $ovulationDay")
        return@withContext nextPeriodDate
    }

    private suspend fun getNextPeriodDate(date: LocalDate): LocalDate? {
        var tempDate = date
        var nextPeriodDate: LocalDate? = null
        while (isContinueRecalculation(tempDate)) {
            tempDate = tempDate.plusDays(1)
            if (getDayUseCase.execute(tempDate).stateOfDay == PERIOD) {
                nextPeriodDate = tempDate
            }
        }
        return nextPeriodDate
    }

    private suspend fun findNearestPeriod(date: LocalDate): LocalDate? {
        var initialDate: LocalDate? = date
        while (initialDate != null
            && getDayUseCase.execute(initialDate).stateOfDay != PERIOD
        ) {
            initialDate = initialDate.minusDays(1)
            if (initialDate.isBefore(date.minusMonths(2))) {
                initialDate = clearStatusDaysUntilNextPeriod(initialDate)
            }
        }
        return initialDate
    }

    private suspend fun clearStatusDaysUntilNextPeriod(start: LocalDate): LocalDate? {
        var tempDate = start
        var nextPeriodDate: LocalDate? = null
        while (isContinueRecalculation(tempDate)) {
            setStateToDay(tempDate, null)
            tempDate = tempDate.plusDays(1)
            if (getDayUseCase.execute(tempDate).stateOfDay == PERIOD) {
                nextPeriodDate = tempDate
            }
        }
        return nextPeriodDate
    }

    private suspend fun calculateAverageInterval(): Long? {
        val intervals = mutableListOf<Interval>()
        val initialDate = LocalDate.now().minusMonths(MAX_COUNT_MONTHS_FOR_INSIGHT)
        var tempDate = initialDate
        var startOfInterval: LocalDate? = null
        var finishOfInterval: LocalDate? = null
        var sumOfIntervals = 0
        while (tempDate.isBefore(LocalDate.now())) {
            if (getDayUseCase.execute(tempDate.minusDays(1)).stateOfDay == PERIOD
                && getDayUseCase.execute(tempDate).stateOfDay != PERIOD
            ) {
                startOfInterval = tempDate
                do {
                    tempDate = tempDate.plusDays(1)
                    if (getDayUseCase.execute(tempDate.plusDays(1)).stateOfDay == PERIOD) {
                        finishOfInterval = tempDate
                    }
                } while (getDayUseCase.execute(tempDate.plusDays(1)).stateOfDay != PERIOD
                    && tempDate.isBefore(LocalDate.now())
                )
                finishOfInterval?.let { intervals.add(Interval(startOfInterval!!, it)) }
                startOfInterval = null
                finishOfInterval = null
            }
            tempDate = tempDate.plusDays(1)
        }
//        intervals.forEach { Timber.d("start: ${it.start}, finish: ${it.finish}") }
//        intervals.forEach { Timber.d("size of interval: ${it.getLengthDays()}") }
        intervals.forEach { sumOfIntervals = sumOfIntervals.plus(it.getLengthDays()) }
        if (intervals.size < MIN_COUNT_PERIODS_FOR_INSIGHT - 1) return null
        return sumOfIntervals.toLong() / intervals.size
    }

    private suspend fun setExpectedPeriodDays(
        averagePeriodLength: Long,
        averageInterval: Long?,
        finishOfPeriod: LocalDate
    ) {
        val start = finishOfPeriod.plusDays(1)
        averageInterval?.let {
            for (i in 0 until averagePeriodLength) {
                val date = start.plusDays(it + i)
                if (!isContinueRecalculation(date)) {
                    return@let
                }
                setStateToDay(date, EXPECTED_NEW_PERIOD)
            }
        }
    }

    private suspend fun calculateAveragePeriod(): Long? {
        val periods = mutableListOf<Period>()
        val initialDate = LocalDate.now().minusMonths(MAX_COUNT_MONTHS_FOR_INSIGHT)
        var tempDate = initialDate
        var startOfPeriod: LocalDate? = null
        var finishOfPeriod: LocalDate? = null
        var sumOfIntervals = 0
        while (tempDate.isBefore(LocalDate.now())) {
            if (getDayUseCase.execute(tempDate.minusDays(1))?.stateOfDay != PERIOD
                && getDayUseCase.execute(tempDate).stateOfDay == PERIOD
            ) {
                startOfPeriod = tempDate
                while (true) {
                    if (getDayUseCase.execute(tempDate).stateOfDay == PERIOD
                        && getDayUseCase.execute(tempDate.plusDays(1)).stateOfDay != PERIOD
                    ) {
                        finishOfPeriod = tempDate
                        periods.add(Period(startOfPeriod, finishOfPeriod))
                        break
                    }
                    tempDate = tempDate.plusDays(1)
                }
            }
            tempDate = tempDate.plusDays(1)
        }
//        periods.forEach { Timber.d("lentht: ${it.getLengthDays().toString()}") }
        periods.forEach { sumOfIntervals = sumOfIntervals.plus(it.getLengthDays()) }
        if (periods.size < MIN_COUNT_PERIODS_FOR_INSIGHT - 1) return null
        return sumOfIntervals.toLong() / periods.size
    }


    private suspend fun setOvulationDay(finishOfPeriod: LocalDate): LocalDate {
        val ovulationDay = finishOfPeriod.plusDays(Constants.OVULATION_DELAY_AFTER_PERIOD.toLong())
        if (getDayUseCase.execute(ovulationDay).stateOfDay == FERTILE)
            setStateToDay(ovulationDay, OVULATION)
        return ovulationDay
    }

    private suspend fun setFertileDays(start: LocalDate): LocalDate {
        var tempDate = start
        for (i in 1 until Constants.SIZE_OF_FERTILE_DAYS) {
            if (isContinueRecalculation(tempDate)) {
                setStateToDay(tempDate, FERTILE)
                tempDate = tempDate.plusDays(1)
            } else {
                return tempDate.minusDays(1)
            }
        }
        return tempDate.minusDays(1)
    }

    private suspend fun setDelayAfterPeriod(start: LocalDate): LocalDate {
        var tempDate = start/*.plusDays(1)*/
        for (i in 0 until Constants.SIZE_OF_DELAY_AFTER_PERIOD) {
            if (isContinueRecalculation(tempDate)) {
                setStateToDay(tempDate, null)
                tempDate = tempDate.plusDays(1)
            } else {
                return tempDate
            }
        }
        return tempDate.minusDays(1)
    }

    private suspend fun setStateToDay(date: LocalDate, stateOfDay: StateOfDay?) {
        val dayFromStorage = getDayUseCase.execute(date)
        saveDayUseCase.execute(dayFromStorage.apply { this.stateOfDay = stateOfDay })
    }

    private suspend fun getStartOfPeriod(initialDate: LocalDate): LocalDate {
        var tempDate = initialDate
        while (getDayUseCase.execute(tempDate.minusDays(1)).stateOfDay == PERIOD) {
            tempDate = tempDate.minusDays(1)
        }
        return tempDate
    }

    private suspend fun getFinishOfPeriod(initialDate: LocalDate): LocalDate {
        var tempDate = initialDate
        while (getDayUseCase.execute(tempDate.plusDays(1)).stateOfDay == PERIOD) {
            tempDate = tempDate.plusDays(1)
        }
        return tempDate
    }

    private suspend fun isContinueRecalculation(date: LocalDate) =
        getDayUseCase.execute(date).stateOfDay != PERIOD
                && date.isBefore(LocalDate.now().plusMonths(2))
}