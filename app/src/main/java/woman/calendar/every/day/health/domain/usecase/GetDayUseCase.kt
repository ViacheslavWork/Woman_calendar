package woman.calendar.every.day.health.domain.usecase

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Day

class GetDayUseCase(val repository: Repository) {
    suspend fun execute(date: LocalDate): Day {
        return repository.getDay(date) ?: Day(date)
    }
}