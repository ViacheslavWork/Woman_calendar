package woman.calendar.every.day.health.domain.usecase

import org.threeten.bp.LocalDate
import timber.log.Timber
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Period
import woman.calendar.every.day.health.domain.model.StateOfDay

private const val MIN_COUNT_PERIODS_FOR_INSIGHT = 2
private const val MAX_COUNT_MONTHS_FOR_INSIGHT = 10L

class RecalculateAveragePeriodLengthUseCase(private val repository: Repository) {
    fun execute(): Long? {
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
}