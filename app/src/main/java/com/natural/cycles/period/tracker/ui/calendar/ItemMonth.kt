package com.natural.cycles.period.tracker.ui.calendar

import org.threeten.bp.LocalDate
import com.natural.cycles.period.tracker.utils.LocalDateHelper.getMonthName

data class ItemMonth(val date: LocalDate, val days: List<ItemDay>) {
    val title: String = "${date.getMonthName()} ${date.year}"
    val daysWithStartDelay = getDaysWithStartDelays()

    private fun getDaysWithStartDelays(): List<ItemDay> {
        val itemsDay = mutableListOf<ItemDay>().apply { addAll(days) }
        val firstWeekDayOfMonth = days[0].date!!.dayOfWeek.value
        for (i in 1 until firstWeekDayOfMonth) {
            itemsDay.add(0, ItemDay())
        }
        return itemsDay
    }
}