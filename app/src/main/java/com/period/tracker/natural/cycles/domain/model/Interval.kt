package com.period.tracker.natural.cycles.domain.model

import org.threeten.bp.LocalDate
import timber.log.Timber

data class Interval(val start: LocalDate, val finish: LocalDate) {
    fun getLengthDays(): Int{
        var length = 1
        var tempDate = start
        while (tempDate.isBefore(finish)) {
            length++
            tempDate = tempDate.plusDays(1)
        }
        Timber.d(length.toString())
        return length
    }
}