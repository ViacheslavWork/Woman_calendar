package com.period.tracker.natural.cycles.domain.usecase.periods

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.domain.Repository
import com.period.tracker.natural.cycles.domain.model.StateOfDay
import com.period.tracker.natural.cycles.utils.Constants

const val MAX_PERIODS = Constants.MIN_COUNT_PERIODS_FOR_INSIGHT
const val MAX_NUMBER_OF_MOUNTS = 12L

class GetMinCountOfPeriodsUseCase(val repository: Repository) {
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