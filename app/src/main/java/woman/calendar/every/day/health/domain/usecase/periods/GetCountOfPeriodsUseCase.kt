package woman.calendar.every.day.health.domain.usecase.periods

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.StateOfDay

const val MAX_PERIODS = 3
const val MAX_NUMBER_OF_MOUNTS = 12L

class GetCountOfPeriodsUseCase(val repository: Repository) {
    suspend fun execute(): Int = withContext(Dispatchers.IO) {
        val initialDate = LocalDate.now()
        var tempDate = initialDate
        var countOfPeriods = 0
        while (countOfPeriods != MAX_PERIODS
            && tempDate.isAfter(initialDate.minusMonths(MAX_NUMBER_OF_MOUNTS))
        ) {
            if (repository.getDay(tempDate)?.stateOfDay == StateOfDay.PERIOD
                && repository.getDay(tempDate.minusDays(1))?.stateOfDay != StateOfDay.PERIOD
            ) {
                countOfPeriods++
            }
            tempDate = tempDate.minusDays(1)
        }
        return@withContext countOfPeriods
    }
}