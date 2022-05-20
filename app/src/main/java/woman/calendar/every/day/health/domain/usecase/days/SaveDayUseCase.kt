package woman.calendar.every.day.health.domain.usecase.days

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Day

class SaveDayUseCase(val repository: Repository) {
    suspend fun execute(day: Day) {
        return repository.setDay(day)
    }
}