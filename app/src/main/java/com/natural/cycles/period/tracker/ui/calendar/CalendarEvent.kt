package com.natural.cycles.period.tracker.ui.calendar

import com.natural.cycles.period.tracker.domain.model.Day

sealed class CalendarEvent(val day: Day) {
    class OnDayClick(day: Day) : CalendarEvent(day)
}
