package com.natural.cycles.period.tracker.ui.calendar

import org.threeten.bp.LocalDate
import com.natural.cycles.period.tracker.domain.model.Day
import com.natural.cycles.period.tracker.domain.model.StateOfDay

data class ItemDay(
    val date: LocalDate? = null,
    var stateOfDay: StateOfDay? = null,
) {
    val numOfDay: String? = date?.dayOfMonth?.toString()
    companion object {
        fun fromDay(day: Day): ItemDay {
            return ItemDay(
                date = day.date,
                stateOfDay = day.stateOfDay
            )
        }
    }
}