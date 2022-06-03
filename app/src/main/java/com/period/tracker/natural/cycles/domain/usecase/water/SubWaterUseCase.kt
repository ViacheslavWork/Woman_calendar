package com.period.tracker.natural.cycles.domain.usecase.water

import com.period.tracker.natural.cycles.domain.usecase.days.GetDayUseCase
import com.period.tracker.natural.cycles.domain.usecase.days.SaveDayUseCase
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