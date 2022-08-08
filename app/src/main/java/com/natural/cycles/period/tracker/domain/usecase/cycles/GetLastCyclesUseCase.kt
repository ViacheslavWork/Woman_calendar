package com.natural.cycles.period.tracker.domain.usecase.cycles

import org.threeten.bp.LocalDate
import com.natural.cycles.period.tracker.domain.Repository
import com.natural.cycles.period.tracker.domain.model.Cycle
import com.natural.cycles.period.tracker.domain.model.Period
import com.natural.cycles.period.tracker.domain.model.StateOfDay.*

private const val MAX_NUMBER_OF_MOUNTS_SEARCH = 12L

class GetLastCyclesUseCase(val repository: Repository) {
    suspend fun execute(numOfLastCycles: Int): List<Cycle> {
        var tempDate = LocalDate.now()
        val cycles = mutableListOf<Cycle>()
        while (cycles.size < numOfLastCycles
            && tempDate.isAfter(LocalDate.now().minusMonths(MAX_NUMBER_OF_MOUNTS_SEARCH))
        ) {
            if (repository.getDay(tempDate)?.stateOfDay == PERIOD) {
                val startOfPeriod = getStartOfPeriod(tempDate)
                val endOfPeriod = getFinishOfPeriod(tempDate)
                val endOfCycle = getFinishOfCycle(endOfPeriod.plusDays(1))
                val startFertile = getStartOfFertile(endOfPeriod)
                val endFertile = startFertile?.let { getEndOfFertile(it) }
                val ovulationDate = startFertile?.let { getOvulationDate(it) }
                val expectedNewPeriodDate = endFertile?.let { getExpectedNewPeriodDate(it) }

                cycles.add(
                    Cycle(
                        start = startOfPeriod,
                        endOfCycle,
                        Period(startOfPeriod, endOfPeriod),
                        startFertile,
                        endFertile,
                        ovulationDate,
                        expectedNewPeriodDate
                    )
                )
                tempDate = startOfPeriod
            }
            tempDate = tempDate.minusDays(1)
        }
        return cycles.toList()
    }

    private suspend fun getExpectedNewPeriodDate(endFertile: LocalDate): LocalDate? {
        var tempDate = endFertile
        while (
            repository.getDay(tempDate)?.stateOfDay != EXPECTED_NEW_PERIOD
            && repository.getDay(tempDate)?.stateOfDay != PERIOD
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
        while (repository.getDay(tempDate)?.stateOfDay != FERTILE
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
        while (repository.getDay(tempDate.plusDays(1))?.stateOfDay == FERTILE
            || repository.getDay(tempDate.plusDays(1))?.stateOfDay == OVULATION
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
        while (repository.getDay(tempDate)?.stateOfDay != OVULATION
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

    private suspend fun getFinishOfCycle(initialDate: LocalDate): LocalDate? {
        var tempDate = initialDate
        while (repository.getDay(tempDate.plusDays(1))?.stateOfDay != PERIOD) {
            tempDate = tempDate.plusDays(1)
            if (tempDate.isAfter(LocalDate.now())) {
                return null
            }
        }
        return tempDate
    }
}