package woman.calendar.every.day.health.domain

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.Day

interface Repository {
    suspend fun setDay(day: Day)
    suspend fun getDay(date: LocalDate): Day?
}