package woman.calendar.every.day.health.domain.usecase.periods

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.Period
import woman.calendar.every.day.health.domain.model.StateOfDay
import woman.calendar.every.day.health.domain.usecase.days.GetDayUseCase

private const val MAX_NUMBER_OF_MOUNTS_SEARCH = 12L

class GetEarliestPeriodUseCase(val getDayUseCase: GetDayUseCase) {
    suspend fun execute(): Period? {
        var date = LocalDate.now()
        var tempDate = LocalDate.now()
        var period: Period? = null
        while (tempDate.isAfter(date.minusMonths(MAX_NUMBER_OF_MOUNTS_SEARCH))) {
            if (getDayUseCase.execute(tempDate).stateOfDay == StateOfDay.PERIOD) {
                val startOfPeriod = getStartOfPeriod(tempDate)
                val endOfPeriod = getFinishOfPeriod(tempDate)
                period = Period(startOfPeriod, endOfPeriod)
                date = startOfPeriod
            }
            tempDate = tempDate.minusDays(1)
        }
        return period
    }

    private suspend fun getStartOfPeriod(initialDate: LocalDate): LocalDate {
        var tempDate = initialDate
        while (getDayUseCase.execute(tempDate.minusDays(1)).stateOfDay == StateOfDay.PERIOD) {
            tempDate = tempDate.minusDays(1)
        }
        return tempDate
    }

    private suspend fun getFinishOfPeriod(initialDate: LocalDate): LocalDate {
        var tempDate = initialDate
        while (getDayUseCase.execute(tempDate.plusDays(1)).stateOfDay == StateOfDay.PERIOD) {
            tempDate = tempDate.plusDays(1)
        }
        return tempDate
    }
}