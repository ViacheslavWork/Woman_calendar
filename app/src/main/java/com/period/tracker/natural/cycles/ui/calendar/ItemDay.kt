package com.period.tracker.natural.cycles.ui.calendar

import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.domain.model.Day
import com.period.tracker.natural.cycles.domain.model.StateOfDay

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