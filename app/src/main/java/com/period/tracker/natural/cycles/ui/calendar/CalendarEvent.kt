package com.period.tracker.natural.cycles.ui.calendar

import com.period.tracker.natural.cycles.domain.model.Day

sealed class CalendarEvent(val day: Day) {
    class OnDayClick(day: Day) : CalendarEvent(day)
}
