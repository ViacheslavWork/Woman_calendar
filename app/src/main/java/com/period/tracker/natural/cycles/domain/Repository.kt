package com.period.tracker.natural.cycles.domain

import com.period.tracker.natural.cycles.domain.model.Day
import org.threeten.bp.LocalDate

interface Repository {
    suspend fun setDay(day: Day)
    suspend fun setDays(days: List<Day>)
    suspend fun getDay(date: LocalDate): Day?
    suspend fun deleteDay(date: LocalDate)
}