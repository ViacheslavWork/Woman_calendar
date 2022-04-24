package woman.calendar.every.day.health.domain.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import timber.log.Timber
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Day
import woman.calendar.every.day.health.domain.model.Interval
import woman.calendar.every.day.health.domain.model.Period
import woman.calendar.every.day.health.domain.model.StateOfDay
import woman.calendar.every.day.health.utils.Constants

private const val MIN_COUNT_PERIODS_FOR_INSIGHT = 2
private const val MAX_COUNT_MONTHS_FOR_INSIGHT = 10L

class RecalculateFromDayUseCase(
    private val repository: Repository,
) {
    val scopeIO = CoroutineScope(Dispatchers.IO)
    suspend fun execute(date: LocalDate) = withContext(Dispatchers.Default) {
        val averagePeriodLength = calculateAveragePeriod() ?: 1L
        val averageInterval = calculateAverageInterval()
        var initialDate: LocalDate? = findNearestPeriod(date)

        while (initialDate != null) {
            initialDate = calculatePeriod(initialDate,averagePeriodLength,averageInterval)
        }
    }

    private suspend fun calculatePeriod(
        dateInPeriod: LocalDate,
        averagePeriodLength: Long,
        averageInterval: Long?
    ): LocalDate? {
//        val startOfPeriod = getStartOfPeriod(dateInPeriod)
        val endOfPeriod = getFinishOfPeriod(dateInPeriod)

        val endDelayAfterPeriod = setDelayAfterPeriod(start = endOfPeriod.plusDays(1))
        val endFertileDays = setFertileDays(start = endDelayAfterPeriod.plusDays(1))
        val ovulationDay = setOvulationDay(endOfPeriod)
        val nextPeriodDate = clearDaysUntilNextPeriod(start = endFertileDays.plusDays(1))
        setExpectedPeriodDays(averagePeriodLength, averageInterval, endOfPeriod)

//        Timber.d("Start of period: $startOfPeriod")
//        Timber.d("Finish of period: $finishOfPeriod")
//        Timber.d("finishDelayAfterPeriod: $finishDelayAfterPeriod")
//        Timber.d("finishFertileDays: $finishFertileDays")
//        Timber.d("averagePeriodLength: $averagePeriodLength")
//        Timber.d("averageInterval: $averageInterval")
//        Timber.d("ovulationDay: $ovulationDay")
        return nextPeriodDate
    }

    private suspend fun findNearestPeriod(date: LocalDate): LocalDate? {
        var initialDate: LocalDate? = date
        while (initialDate != null
            && repository.getDay(initialDate)?.stateOfDay != StateOfDay.PERIOD
        ) {
            initialDate = initialDate.minusDays(1)
            if (initialDate.isBefore(date.minusMonths(2))) {
                initialDate = clearDaysUntilNextPeriod(initialDate)
            }
        }
        return initialDate
    }

    private suspend fun clearDaysUntilNextPeriod(start: LocalDate): LocalDate? {
        var tempDate = start
        var nextPeriodDate: LocalDate? = null
        while (isContinueRecalculation(tempDate)) {
            repository.setDay(Day(tempDate))
            tempDate = tempDate.plusDays(1)
            if (repository.getDay(tempDate)?.stateOfDay == StateOfDay.PERIOD) {
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
            if (repository.getDay(tempDate.minusDays(1))?.stateOfDay == StateOfDay.PERIOD
                && repository.getDay(tempDate)?.stateOfDay != StateOfDay.PERIOD
            ) {
                startOfInterval = tempDate
                do {
                    tempDate = tempDate.plusDays(1)
                    if (repository.getDay(tempDate.plusDays(1))?.stateOfDay == StateOfDay.PERIOD) {
                        finishOfInterval = tempDate
                    }
                } while (repository.getDay(tempDate.plusDays(1))?.stateOfDay != StateOfDay.PERIOD
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
                repository.setDay(Day(date, StateOfDay.EXPECTED_NEW_PERIOD))
                Timber.d("expected date: $date")
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
            if (repository.getDay(tempDate.minusDays(1))?.stateOfDay != StateOfDay.PERIOD
                && repository.getDay(tempDate)?.stateOfDay == StateOfDay.PERIOD
            ) {
                startOfPeriod = tempDate
                while (repository.getDay(tempDate) != null) {
                    if (repository.getDay(tempDate)?.stateOfDay == StateOfDay.PERIOD
                        && repository.getDay(tempDate.plusDays(1))?.stateOfDay != StateOfDay.PERIOD
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
        periods.forEach { Timber.d("lentht: ${it.getLengthDays().toString()}") }
        periods.forEach { sumOfIntervals = sumOfIntervals.plus(it.getLengthDays()) }
        if (periods.size < MIN_COUNT_PERIODS_FOR_INSIGHT - 1) return null
        return sumOfIntervals.toLong() / periods.size
    }


    private suspend fun setOvulationDay(finishOfPeriod: LocalDate): LocalDate {
        val ovulationDay = finishOfPeriod.plusDays(Constants.OVULATION_DELAY_AFTER_PERIOD.toLong())
        if (repository.getDay(ovulationDay)?.stateOfDay == StateOfDay.FERTILE)
            repository.setDay(Day(ovulationDay, StateOfDay.OVULATION))
        return ovulationDay
    }

    private suspend fun setFertileDays(start: LocalDate): LocalDate {
        var tempDate = start
        for (i in 1 until Constants.SIZE_OF_FERTILE_DAYS) {
            if (isContinueRecalculation(tempDate)) {
                repository.setDay(Day(tempDate, StateOfDay.FERTILE))
                tempDate = tempDate.plusDays(1)
            } else {
                return tempDate
            }
        }
        return tempDate.minusDays(1)
    }

    private suspend fun setDelayAfterPeriod(start: LocalDate): LocalDate {
        var tempDate = start/*.plusDays(1)*/
        for (i in 0 until Constants.SIZE_OF_DELAY_AFTER_PERIOD) {
            if (isContinueRecalculation(tempDate)) {
                repository.setDay(Day(tempDate, null))
                tempDate = tempDate.plusDays(1)
            } else {
                return tempDate
            }
        }
        return tempDate.minusDays(1)
    }

    private suspend fun getStartOfPeriod(initialDate: LocalDate): LocalDate {
        var tempDate = initialDate
        while (repository.getDay(tempDate.minusDays(1))?.stateOfDay == StateOfDay.PERIOD) {
            tempDate = tempDate.minusDays(1)
        }
        return tempDate
    }

    private suspend fun getFinishOfPeriod(initialDate: LocalDate): LocalDate {
        var tempDate = initialDate
        while (repository.getDay(tempDate.plusDays(1))?.stateOfDay == StateOfDay.PERIOD) {
            tempDate = tempDate.plusDays(1)
        }
        return tempDate
    }

    private suspend fun isContinueRecalculation(date: LocalDate) =
        repository.getDay(date)?.stateOfDay != StateOfDay.PERIOD
                && date.isBefore(LocalDate.now().plusMonths(2))
}