package woman.calendar.every.day.health.domain.usecase.cycles

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.Cycle
import woman.calendar.every.day.health.domain.model.Period
import woman.calendar.every.day.health.domain.model.StateOfDay
import woman.calendar.every.day.health.domain.usecase.days.GetDayUseCase

private const val MAX_NUMBER_OF_MOUNTS_SEARCH = 12L

class GetCycleUseCase(private val getDayUseCase: GetDayUseCase) {
    suspend fun execute(dateInCycle: LocalDate): Cycle? {
        var tempDate = dateInCycle
        while (tempDate.isAfter(dateInCycle.minusMonths(MAX_NUMBER_OF_MOUNTS_SEARCH))) {
            if (getDayUseCase.execute(tempDate).stateOfDay == StateOfDay.PERIOD) {
                val startOfPeriod = getStartOfPeriod(tempDate)
                val endOfPeriod = getFinishOfPeriod(tempDate)
                val endOfCycle = getFinishOfCycle(endOfPeriod.plusDays(1))
                val startFertile = getStartOfFertile(endOfPeriod)
                val endFertile = startFertile?.let { getEndOfFertile(it) }
                val ovulationDate = startFertile?.let { getOvulationDate(it) }
                val expectedNewPeriodDate = endFertile?.let { getExpectedNewPeriodDate(it) }
                return Cycle(
                    start = startOfPeriod,
                    endOfCycle,
                    Period(startOfPeriod, endOfPeriod),
                    startFertile,
                    endFertile,
                    ovulationDate,
                    expectedNewPeriodDate
                )
            }
            tempDate = tempDate.minusDays(1)
        }
        return null
    }


    private suspend fun getExpectedNewPeriodDate(endFertile: LocalDate): LocalDate? {
        var tempDate = endFertile
        while (
            getDayUseCase.execute(tempDate).stateOfDay != StateOfDay.EXPECTED_NEW_PERIOD
            && getDayUseCase.execute(tempDate).stateOfDay != StateOfDay.PERIOD
        ) {
            if (tempDate.isAfter(LocalDate.now().plusMonths(MAX_NUMBER_OF_MOUNTS_SEARCH))) {
                return null
            }
            tempDate = tempDate.plusDays(1)
        }
        return tempDate
    }

    private suspend fun getStartOfFertile(endOfPeriod: LocalDate): LocalDate? {
        var tempDate = endOfPeriod
        while (getDayUseCase.execute(tempDate).stateOfDay != StateOfDay.FERTILE
        ) {
            if (tempDate.isAfter(LocalDate.now().plusMonths(MAX_NUMBER_OF_MOUNTS_SEARCH))) {
                return null
            }
            tempDate = tempDate.plusDays(1)
        }
        return tempDate
    }

    private suspend fun getEndOfFertile(startFertile: LocalDate): LocalDate? {
        var tempDate = startFertile
        while (getDayUseCase.execute(tempDate.plusDays(1)).stateOfDay == StateOfDay.FERTILE
            || getDayUseCase.execute(tempDate.plusDays(1)).stateOfDay == StateOfDay.OVULATION
        ) {
            if (tempDate.isAfter(LocalDate.now().plusMonths(MAX_NUMBER_OF_MOUNTS_SEARCH))) {
                return null
            }
            tempDate = tempDate.plusDays(1)
        }
        return tempDate
    }

    private suspend fun getOvulationDate(startFertile: LocalDate): LocalDate? {
        var tempDate = startFertile
        while (getDayUseCase.execute(tempDate).stateOfDay != StateOfDay.OVULATION
        ) {
            if (tempDate.isAfter(LocalDate.now().plusMonths(MAX_NUMBER_OF_MOUNTS_SEARCH))) {
                return null
            }
            tempDate = tempDate.plusDays(1)
        }
        return tempDate
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

    private suspend fun getFinishOfCycle(initialDate: LocalDate): LocalDate? {
        var tempDate = initialDate
        while (getDayUseCase.execute(tempDate.plusDays(1)).stateOfDay != StateOfDay.PERIOD) {
            tempDate = tempDate.plusDays(1)
            if (tempDate.isAfter(LocalDate.now())) {
                return null
            }
        }
        return tempDate
    }
}