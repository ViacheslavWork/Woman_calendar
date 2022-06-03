package com.period.tracker.natural.cycles.domain.usecase.water

import com.period.tracker.natural.cycles.domain.usecase.days.GetDayUseCase
import com.period.tracker.natural.cycles.domain.usecase.days.SaveDayUseCase
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