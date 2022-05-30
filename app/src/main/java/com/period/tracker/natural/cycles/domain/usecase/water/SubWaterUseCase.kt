package com.period.tracker.natural.cycles.domain.usecase.water

import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.domain.Repository
import com.period.tracker.natural.cycles.domain.usecase.days.GetDayUseCase

class SubWaterUseCase(private val getDayUseCase: GetDayUseCase, val repository: Repository) {
    suspend fun execute(date: LocalDate, volumeOfWaterForSub: Float) {
        repository.setDay(
            getDayUseCase.execute(date)
                .apply {
                    volumeOfWater -= volumeOfWaterForSub
                    if (volumeOfWater < 0) volumeOfWater = 0F
                }
        )
    }
}