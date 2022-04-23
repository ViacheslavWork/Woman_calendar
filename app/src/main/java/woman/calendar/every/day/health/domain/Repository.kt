package woman.calendar.every.day.health.domain

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.Day

interface Repository {
    fun setDay(day: Day)
    fun getDay(date: LocalDate): Day?
}