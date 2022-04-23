package woman.calendar.every.day.health.ui.calendar

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.Day
import java.util.*

sealed class CalendarEvent(val day: Day) {
    class OnDayClick(day: Day) : CalendarEvent(day)
}
