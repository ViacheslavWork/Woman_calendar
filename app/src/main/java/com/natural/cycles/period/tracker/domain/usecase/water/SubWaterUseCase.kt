package com.natural.cycles.period.tracker.domain.usecase.water

import com.natural.cycles.period.tracker.domain.usecase.days.GetDayUseCase
import com.natural.cycles.period.tracker.domain.usecase.days.SaveDayUseCase
import org.threeten.bp.LocalDate

class SubWaterUseCase(
    private val getDayUseCase: GetDayUseCase,
    private val saveDayUseCase: SaveDayUseCase
) {
    suspend fun execute(date: LocalDate, volumeOfWaterForSub: Float) {
        saveDayUseCase.execute(
            getDayUseCase.execute(date)
                .apply {
                    volumeOfWater -= volumeOfWaterForSub
                    if (volumeOfWater < 0) volumeOfWater = 0F
                }
        )
    }
}