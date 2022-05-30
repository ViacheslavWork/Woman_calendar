package com.period.tracker.natural.cycles.domain.usecase.water

import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.domain.Repository
import com.period.tracker.natural.cycles.domain.usecase.days.GetDayUseCase

class AddWaterUseCase(private val getDayUseCase: GetDayUseCase, val repository: Repository) {
    suspend fun execute(date: LocalDate, volumeOfWaterForAdd: Float) {
        repository.setDay(getDayUseCase.execute(date)
            .apply { this.volumeOfWater += volumeOfWaterForAdd })
    }
}