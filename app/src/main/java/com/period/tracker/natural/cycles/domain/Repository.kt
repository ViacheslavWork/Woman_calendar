package com.period.tracker.natural.cycles.domain

import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.domain.model.Day

interface Repository {
    suspend fun setDay(day: Day)
    suspend fun getDay(date: LocalDate): Day?
    suspend fun deleteDay(date: LocalDate)
}