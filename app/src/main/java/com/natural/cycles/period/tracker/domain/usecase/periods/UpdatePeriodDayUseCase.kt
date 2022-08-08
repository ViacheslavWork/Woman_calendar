package com.natural.cycles.period.tracker.domain.usecase.periods

import org.threeten.bp.LocalDate
import com.natural.cycles.period.tracker.domain.Repository
import com.natural.cycles.period.tracker.domain.model.Day
import com.natural.cycles.period.tracker.domain.usecase.RecalculateFromDayUseCase

class UpdatePeriodDayUseCase(
    private val repository: Repository,
    private val recalculateFromDayUseCase: RecalculateFromDayUseCase
) {
    suspend fun execute(day: Day) {
        if (day.date.isAfter(LocalDate.now())) return
//        repository.setDay(day)
//        recalculateFromDayUseCase.execute(date = day.date)
    }
}