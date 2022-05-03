package woman.calendar.every.day.health.domain.usecase.water

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.usecase.days.GetDayUseCase

class AddWaterUseCase(private val getDayUseCase: GetDayUseCase, val repository: Repository) {
    suspend fun execute(date: LocalDate, volumeOfWaterForAdd: Float) {
        repository.setDay(getDayUseCase.execute(date)
            .apply { this.volumeOfWater += volumeOfWaterForAdd })
    }
}