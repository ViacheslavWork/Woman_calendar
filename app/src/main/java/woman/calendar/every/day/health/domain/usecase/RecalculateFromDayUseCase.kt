package woman.calendar.every.day.health.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import timber.log.Timber
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Interval
import woman.calendar.every.day.health.domain.model.Period
import woman.calendar.every.day.health.domain.model.StateOfDay
import woman.calendar.every.day.health.domain.model.StateOfDay.*
import woman.calendar.every.day.health.domain.usecase.days.GetDayUseCase
import woman.calendar.every.day.health.utils.Constants

private const val MIN_COUNT_PERIODS_FOR_INSIGHT = 2
private const val MAX_COUNT_MONTHS_FOR_INSIGHT = 10L

class RecalculateFromDayUseCase(
    private val repository: Repository,
    private val getDayUseCase: GetDayUseCase
) {
    suspend fun execute(date: LocalDate) = withContext(Dispatchers.IO) {
        val startTime = System.currentTimeMillis()
        val averagePeriodLength = async { calculateAveragePeriod() ?: 1L }
        val averageInterval = async { calculateAverageInterval() }
        var initialDate: LocalDate? = findNearestPeriod(date)

        while (initialDate != null) {
//            val startTime = System.currentTimeMillis()
            initialDate =
                calculateCycle(initialDate, averagePeriodLength.await(), averageInterval.await())
            Timber.d("Calculate period time: ${System.currentTimeMillis() - startTime}")
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
            launch {
                setExpectedPeriodDays(
                    averagePeriodLength,
                    averageInterval,
                    endOfPeriod.await()
                )
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
            if (repository.getDay(tempDate)?.stateOfDay == PERIOD) {
                nextPeriodDate = tempDate
            }
        }
        return nextPeriodDate
    }

    private suspend fun findNearestPeriod(date: LocalDate): LocalDate? {
        var initialDate: LocalDate? = date
        while (initialDate != null
            && repository.getDay(initialDate)?.stateOfDay != PERIOD
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
            if (repository.getDay(tempDate)?.stateOfDay == PERIOD) {
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
            if (repository.getDay(tempDate.minusDays(1))?.stateOfDay == PERIOD
                && repository.getDay(tempDate)?.stateOfDay != PERIOD
            ) {
                startOfInterval = tempDate
                do {
                    tempDate = tempDate.plusDays(1)
                    if (repository.getDay(tempDate.plusDays(1))?.stateOfDay == PERIOD) {
                        finishOfInterval = tempDate
                    }
                } while (repository.getDay(tempDate.plusDays(1))?.stateOfDay != PERIOD
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
            if (repository.getDay(tempDate.minusDays(1))?.stateOfDay != PERIOD
                && repository.getDay(tempDate)?.stateOfDay == PERIOD
            ) {
                startOfPeriod = tempDate
                while (repository.getDay(tempDate) != null) {
                    if (repository.getDay(tempDate)?.stateOfDay == PERIOD
                        && repository.getDay(tempDate.plusDays(1))?.stateOfDay != PERIOD
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
        if (repository.getDay(ovulationDay)?.stateOfDay == FERTILE)
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
        repository.setDay(dayFromStorage.apply { this.stateOfDay = stateOfDay })
    }

    private suspend fun getStartOfPeriod(initialDate: LocalDate): LocalDate {
        var tempDate = initialDate
        while (repository.getDay(tempDate.minusDays(1))?.stateOfDay == PERIOD) {
            tempDate = tempDate.minusDays(1)
        }
        return tempDate
    }

    private suspend fun getFinishOfPeriod(initialDate: LocalDate): LocalDate {
        var tempDate = initialDate
        while (repository.getDay(tempDate.plusDays(1))?.stateOfDay == PERIOD) {
            tempDate = tempDate.plusDays(1)
        }
        return tempDate
    }

    private suspend fun isContinueRecalculation(date: LocalDate) =
        repository.getDay(date)?.stateOfDay != PERIOD
                && date.isBefore(LocalDate.now().plusMonths(2))
}