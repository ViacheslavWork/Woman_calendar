package woman.calendar.every.day.health.domain.usecase

import org.threeten.bp.LocalDate
import timber.log.Timber
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Day
import woman.calendar.every.day.health.domain.model.Interval
import woman.calendar.every.day.health.domain.model.StateOfDay
import woman.calendar.every.day.health.utils.Constants

private const val MIN_COUNT_PERIODS_FOR_INSIGHT = 2
private const val MAX_COUNT_MONTHS_FOR_INSIGHT = 10L

class RecalculateFromDayUseCase(
    private val repository: Repository,
    private val recalculateAveragePeriodIntervalUseCase: RecalculateAveragePeriodIntervalUseCase
) {
    fun execute(date: LocalDate) {
        var initialDate: LocalDate? = date
        if (repository.getDay(initialDate!!.minusDays(1))?.stateOfDay == StateOfDay.PERIOD) {
            initialDate = initialDate.minusDays(1)
        }
        initialDate?.let {
            if (repository.getDay(it)?.stateOfDay != StateOfDay.PERIOD) {
                initialDate = clearDaysUntilNextPeriod(start = it)
            }
        }
        initialDate?.let {
            val startOfPeriod = getStartOfPeriod(it)
            val endOfPeriod = getFinishOfPeriod(it)
            val averagePeriodLength = RecalculateAveragePeriodLengthUseCase(repository).execute() ?: 1L
            val averageInterval = calculateAverageInterval()

            val endDelayAfterPeriod = setDelayAfterPeriod(start = endOfPeriod.plusDays(1))
            val endFertileDays = setFertileDays(start = endDelayAfterPeriod.plusDays(1))
            val ovulationDay = setOvulationDay(endOfPeriod)
            clearDaysUntilNextPeriod(start = endFertileDays.plusDays(1))
            setExpectedPeriodDays(averagePeriodLength, averageInterval, endOfPeriod)

//        Timber.d("Start of period: $startOfPeriod")
//        Timber.d("Finish of period: $finishOfPeriod")
//        Timber.d("finishDelayAfterPeriod: $finishDelayAfterPeriod")
//        Timber.d("finishFertileDays: $finishFertileDays")
            Timber.d("averagePeriodLength: $averagePeriodLength")
//        Timber.d("averageInterval: $averageInterval")
//        Timber.d("ovulationDay: $ovulationDay")
        }

    }

    private fun clearDaysUntilNextPeriod(start: LocalDate): LocalDate? {
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

    private fun calculateAverageInterval(): Long? {
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

    private fun setExpectedPeriodDays(
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

    private fun setOvulationDay(finishOfPeriod: LocalDate): LocalDate {
        val ovulationDay = finishOfPeriod.plusDays(Constants.OVULATION_DELAY_AFTER_PERIOD.toLong())
        if (repository.getDay(ovulationDay)?.stateOfDay == StateOfDay.FERTILE)
            repository.setDay(Day(ovulationDay, StateOfDay.OVULATION))
        return ovulationDay
    }

    private fun setFertileDays(start: LocalDate): LocalDate {
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

    private fun setDelayAfterPeriod(start: LocalDate): LocalDate {
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

    private fun getStartOfPeriod(initialDate: LocalDate): LocalDate {
        var tempDate = initialDate
        while (repository.getDay(tempDate.minusDays(1))?.stateOfDay == StateOfDay.PERIOD) {
            tempDate = tempDate.minusDays(1)
        }
        return tempDate
    }

    private fun getFinishOfPeriod(initialDate: LocalDate): LocalDate {
        var tempDate = initialDate
        while (repository.getDay(tempDate.plusDays(1))?.stateOfDay == StateOfDay.PERIOD) {
            tempDate = tempDate.plusDays(1)
        }
        return tempDate
    }

    private fun isContinueRecalculation(date: LocalDate) =
        repository.getDay(date)?.stateOfDay != StateOfDay.PERIOD
                && date.isBefore(LocalDate.now().plusDays(30))
}