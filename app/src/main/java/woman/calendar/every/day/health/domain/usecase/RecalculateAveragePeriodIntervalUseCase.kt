package woman.calendar.every.day.health.domain.usecase

import org.threeten.bp.LocalDate
import timber.log.Timber
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Interval
import woman.calendar.every.day.health.domain.model.StateOfDay

private const val MIN_COUNT_PERIODS_FOR_INSIGHT = 2
private const val MAX_COUNT_PERIODS_FOR_INSIGHT = 10
private const val MAX_COUNT_MONTHS_FOR_INSIGHT = 10L

class RecalculateAveragePeriodIntervalUseCase(private val repository: Repository) {
    fun execute(): Long? {
        val intervals = mutableListOf<Interval>()
        val initialDate = LocalDate.now().minusMonths(MAX_COUNT_MONTHS_FOR_INSIGHT)
        var tempDate = initialDate
        var startOfInterval: LocalDate? = null
        var finishOfInterval: LocalDate? = null
        var sumOfIntervals = 0
        while (tempDate.isBefore(LocalDate.now())) {
            if (repository.getDay(tempDate.minusDays(1))?.stateOfDay != StateOfDay.PERIOD
                && repository.getDay(tempDate)?.stateOfDay == StateOfDay.PERIOD
            ) {
                if (startOfInterval == null) {
                    startOfInterval = tempDate
                } else {
                    finishOfInterval = tempDate.minusDays(1)
                    intervals.add(Interval(startOfInterval, finishOfInterval))
                    startOfInterval = null
                    finishOfInterval = null
                }
            }
            tempDate = tempDate.plusDays(1)
        }
        intervals.forEach { Timber.d(it.getLengthDays().toString()) }
        intervals.forEach { sumOfIntervals = sumOfIntervals.plus(it.getLengthDays()) }
        if (intervals.size < MIN_COUNT_PERIODS_FOR_INSIGHT - 1) return null
        return sumOfIntervals.toLong() / intervals.size - 1
    }
}