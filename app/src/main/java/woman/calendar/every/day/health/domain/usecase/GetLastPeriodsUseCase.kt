package woman.calendar.every.day.health.domain.usecase

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Period
import woman.calendar.every.day.health.domain.model.StateOfDay

private const val MAX_NUMBER_OF_MOUNTS_SEARCH = 12L
class GetLastPeriodsUseCase(val repository: Repository) {
    suspend fun execute(numOfLastPeriods: Int):List<Period> {
        var tempDate = LocalDate.now()
        val periods = mutableListOf<Period>()
        while (periods.size < numOfLastPeriods
            && tempDate.isAfter(LocalDate.now().minusMonths(MAX_NUMBER_OF_MOUNTS_SEARCH))
        ) {
            if (repository.getDay(tempDate)?.stateOfDay == StateOfDay.PERIOD) {
                val startOfPeriod = getStartOfPeriod(tempDate)
                val endOfPeriod = getFinishOfPeriod(tempDate)
                periods.add(Period(startOfPeriod, endOfPeriod))
                tempDate = startOfPeriod
            }
            tempDate = tempDate.minusDays(1)
        }
        return periods.toList()
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
}