package com.period.tracker.natural.cycles.ui.home

import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.domain.model.Day
import com.period.tracker.natural.cycles.domain.model.StateOfDay

data class ItemDayOfWeek(
    val date: LocalDate,
    var stateOfDay: StateOfDay? = null,
) {
    val numOfDay: String = date.dayOfMonth.toString()
    val dayOfWeek: String = date.dayOfWeek.toString().first().toString()
    companion object {
        fun fromDay(day: Day): ItemDayOfWeek {
            return ItemDayOfWeek(
                date = day.date,
                stateOfDay = day.stateOfDay
            )
        }
    }
}