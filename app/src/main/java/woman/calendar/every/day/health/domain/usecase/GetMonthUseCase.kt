package woman.calendar.every.day.health.domain.usecase

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Day

class GetMonthUseCase(val repository: Repository) {
    fun execute(dateInNeededMonth: LocalDate): List<Day> {
        return getMonth(dateInNeededMonth)
    }

    private fun getMonth(dateInNeededMonth: LocalDate): List<Day> {
        var dayInMonth = LocalDate.of(dateInNeededMonth.year, dateInNeededMonth.month, 1)
        val dates = mutableListOf<LocalDate>()
        val days = mutableListOf<Day>()
        while (dayInMonth.month == dateInNeededMonth.month) {
            dates.add(dayInMonth)
            dayInMonth = dayInMonth.plusDays(1)
        }
        days.addAll(dates.map { repository.getDay(it) ?: Day(date = it) })
        return days.toList()
    }
}