package com.natural.cycles.period.tracker.domain.usecase.water

import com.natural.cycles.period.tracker.domain.usecase.days.GetDayUseCase
import com.natural.cycles.period.tracker.domain.usecase.days.SaveDayUseCase
import org.threeten.bp.LocalDate

class AddWaterUseCase(
    private val getDayUseCase: GetDayUseCase,
    private val saveDayUseCase: SaveDayUseCase
) {
    suspend fun execute(date: LocalDate, volumeOfWaterForAdd: Float) {
        saveDayUseCase.execute(
            getDayUseCase.execute(date)
                .apply { this.volumeOfWater += volumeOfWaterForAdd })
    }
}