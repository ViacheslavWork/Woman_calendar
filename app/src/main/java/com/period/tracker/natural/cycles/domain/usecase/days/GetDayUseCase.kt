package com.period.tracker.natural.cycles.domain.usecase.days

import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.domain.Repository
import com.period.tracker.natural.cycles.domain.model.Day

class GetDayUseCase(val repository: Repository) {
    suspend fun execute(date: LocalDate): Day {
        return repository.getDay(date) ?: Day(date)
    }
}