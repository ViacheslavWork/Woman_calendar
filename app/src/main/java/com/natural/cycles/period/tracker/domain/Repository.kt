package com.natural.cycles.period.tracker.domain

import com.natural.cycles.period.tracker.domain.model.Day
import org.threeten.bp.LocalDate

interface Repository {
    suspend fun setDay(day: Day)
    suspend fun setDays(days: List<Day>)
    suspend fun getDay(date: LocalDate): Day?
    suspend fun deleteDay(date: LocalDate)
}