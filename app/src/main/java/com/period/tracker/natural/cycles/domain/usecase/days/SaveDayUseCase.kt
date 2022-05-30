package com.period.tracker.natural.cycles.domain.usecase.days

import com.period.tracker.natural.cycles.domain.Repository
import com.period.tracker.natural.cycles.domain.model.Day

class SaveDayUseCase(val repository: Repository) {
    suspend fun execute(day: Day) {
        return repository.setDay(day)
    }
}