package com.natural.cycles.period.tracker.domain.usecase.days

import org.threeten.bp.LocalDate
import com.natural.cycles.period.tracker.domain.Repository
import com.natural.cycles.period.tracker.domain.model.Day

class GetDayUseCase(val repository: Repository) {
    suspend fun execute(date: LocalDate): Day {
        return repository.getDay(date) ?: Day(date)
    }
}