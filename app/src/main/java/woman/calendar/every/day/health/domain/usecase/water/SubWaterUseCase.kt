package woman.calendar.every.day.health.domain.usecase.water

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.usecase.GetDayUseCase

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