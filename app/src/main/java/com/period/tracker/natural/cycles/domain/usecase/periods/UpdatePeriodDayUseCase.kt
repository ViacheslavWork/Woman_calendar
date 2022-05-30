package com.period.tracker.natural.cycles.domain.usecase.periods

import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.domain.Repository
import com.period.tracker.natural.cycles.domain.model.Day
import com.period.tracker.natural.cycles.domain.usecase.RecalculateFromDayUseCase

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