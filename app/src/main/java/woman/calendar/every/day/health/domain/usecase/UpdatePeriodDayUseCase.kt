package woman.calendar.every.day.health.domain.usecase

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Day
import woman.calendar.every.day.health.domain.model.StateOfDay

class UpdatePeriodDayUseCase(
    private val repository: Repository,
    private val recalculateFromDayUseCase: RecalculateFromDayUseCase
) {
    suspend fun execute(day: Day) {
        if (day.date.isAfter(LocalDate.now())) return
        repository.setDay(day)
        recalculateFromDayUseCase.execute(date = day.date)
    }
}