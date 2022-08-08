package com.natural.cycles.period.tracker.domain.model

import org.threeten.bp.LocalDate
import timber.log.Timber
import com.natural.cycles.period.tracker.domain.model.CycleStatus.*
import com.natural.cycles.period.tracker.domain.model.DailyNotificationStatus.*

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
    fun getDaysAfterStartOfPeriod(date: LocalDate): Int {
        var tempDate = start
        var daysAfterPeriod = 1
        while (tempDate != date) {
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

    fun getDailyNotificationStatus(date: LocalDate): DailyNotificationStatus {
        return when {
            date == period.start -> PERIOD_START
            date.isAfter(period.start) && date.isBefore(period.finish) -> PERIOD_MID
            date == period.finish -> PERIOD_END
            date.isAfter(period.finish) && date.isBefore(fertileStart) -> POST_PERIOD
            date == fertileStart -> FERTILE_START
            date == ovulationDay -> OVULATION_DAY
            date.isAfter(fertileStart) && date.isBefore(fertileEnd) -> FERTILE_MID
            date == fertileEnd -> FERTILE_END
            date == fertileEnd?.plusDays(1) -> POST_FERTILE_START
            date.isAfter(fertileEnd?.plusDays(1)) && date.isBefore(expectedNewPeriod?.minusDays(1)) -> POST_FERTILE_MID
            date == expectedNewPeriod?.minusDays(1) -> POST_FERTILE_END
            date == expectedNewPeriod -> PRE_PERIOD_START
            //TODO
            date.isAfter(expectedNewPeriod) -> PRE_PERIOD_MID
            else -> PRE_PERIOD_END
        }
    }
}

enum class CycleStatus {
    PERIOD,
    FERTILE_BEFORE_OVULATION,
    FERTILE_AFTER_OVULATION,
    EXPECTED_NEW_PERIOD
}
