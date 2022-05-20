package woman.calendar.every.day.health.domain.usecase.periods

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Day
import woman.calendar.every.day.health.domain.model.StateOfDay.EXPECTED_NEW_PERIOD
import woman.calendar.every.day.health.domain.model.StateOfDay.PERIOD
import woman.calendar.every.day.health.domain.usecase.days.GetDayUseCase
import woman.calendar.every.day.health.utils.Constants

class MarkDayUseCase(
    private val repository: Repository,
    private val getDayUseCase: GetDayUseCase
) {
    suspend fun execute(day: Day) = withContext(Dispatchers.IO) {
        if (day.stateOfDay == PERIOD) {
            if (getDayUseCase.execute(day.date).stateOfDay == EXPECTED_NEW_PERIOD) {
                overrideExpectedDays(day)
            } else {
                launch { repository.setDay(getDayUseCase.execute(day.date).apply { stateOfDay = day.stateOfDay }) }
                launch { findPeriodDaysBefore(day) }
                launch { findPeriodDaysAfter(day) }
            }
            return@withContext
        }
        repository.setDay(getDayUseCase.execute(day.date).apply { stateOfDay = day.stateOfDay })
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
                    repository.setDay(
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
                    repository.setDay(
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
        while (repository.getDay(tempDate)?.stateOfDay == EXPECTED_NEW_PERIOD) {
            repository.setDay(getDayUseCase.execute(tempDate).apply { stateOfDay = PERIOD })
            tempDate = tempDate.plusDays(1)
        }
    }
}