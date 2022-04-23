package woman.calendar.every.day.health.domain.usecase

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Day
import woman.calendar.every.day.health.domain.model.StateOfDay

class UnmarkPeriodDayUseCase(
    private val repository: Repository,
    private val recalculateFromDayUseCase: RecalculateFromDayUseCase
) {
    fun execute(date: LocalDate) {
        if (date.isAfter(LocalDate.now())) return
        repository.setDay(Day(date,null))
        recalculateFromDayUseCase.execute(date = date)
    }
}