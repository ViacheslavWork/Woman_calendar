package woman.calendar.every.day.health.domain.model

import org.threeten.bp.LocalDate
import timber.log.Timber
import woman.calendar.every.day.health.domain.model.CycleStatus.*

data class Cycle(
    val start: LocalDate,
    val finish: LocalDate?,
    val period: Period,
    val fertileStart: LocalDate?,
    val fertileEnd: LocalDate?,
    val ovulationDay: LocalDate?,
    val expectedNewPeriod: LocalDate?
) {
    val currentCycleStatus: CycleStatus?
        get() = getCurrentStatus()

    private fun getCurrentStatus(): CycleStatus? {
        val currentDate = LocalDate.now()
        return when {
            currentDate.isAfter(period.start.minusDays(1))
                    && currentDate.isBefore(period.finish.plusDays(1)) -> {
                PERIOD
            }
            fertileStart?.let { currentDate.isAfter(it.minusDays(1)) } == true
                    && ovulationDay?.let { currentDate.isBefore(it.plusDays(1)) } == true -> {
                FERTILE_BEFORE_OVULATION
            }
            ovulationDay?.let { currentDate.isAfter(ovulationDay) } == true
                    && fertileEnd?.let { currentDate.isBefore(it.plusDays(1)) } == true -> {
                FERTILE_AFTER_OVULATION
            }
            expectedNewPeriod?.let { currentDate == it || currentDate.isAfter(it) } == true -> {
                EXPECTED_NEW_PERIOD
            }
            else -> null
        }
    }

    fun getLengthDays(): Int {
        var length = 0
        var tempDate = start
        val endOfCycle: LocalDate = finish ?: LocalDate.now()
        while (tempDate <= endOfCycle) {
            length++
            tempDate = tempDate.plusDays(1)
        }
        Timber.d(length.toString())
        return length
    }

    fun getDaysAfterStartOfPeriod(): Int {
        var tempDate = start
        var daysAfterPeriod = 1
        while (tempDate != LocalDate.now()) {
            tempDate = tempDate.plusDays(1)
            daysAfterPeriod++
        }
        return daysAfterPeriod
    }

    fun getDaysBeforeOvulation(): Int? {
        var tempDate = LocalDate.now()
        var daysBeforeOvulation = 0
        while (tempDate != ovulationDay) {
            if (tempDate.isAfter(LocalDate.now().plusMonths(2))) {
                return null
            }
            tempDate = tempDate.plusDays(1)
            daysBeforeOvulation++
        }
        return daysBeforeOvulation
    }
}

enum class CycleStatus {
    FERTILE_BEFORE_OVULATION, FERTILE_AFTER_OVULATION, PERIOD, EXPECTED_NEW_PERIOD
}