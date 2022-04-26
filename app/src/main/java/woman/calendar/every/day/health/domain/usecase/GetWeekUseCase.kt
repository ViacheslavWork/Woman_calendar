package woman.calendar.every.day.health.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import timber.log.Timber
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Day

class GetWeekUseCase(private val repository: Repository) {
    suspend fun execute(date: LocalDate): List<Day> = withContext(Dispatchers.IO) {
        return@withContext getWeek(dateInNeededWeek = date)
    }

    private suspend fun getWeek(dateInNeededWeek: LocalDate): List<Day> {
        var dayInWeek = getStartOfWeek(dateInNeededWeek)
        val dates = mutableListOf<LocalDate>()
        val days = mutableListOf<Day>()
        do {
            dayInWeek = dayInWeek.plusDays(1)
            dates.add(dayInWeek)
        } while (dayInWeek.dayOfWeek != DayOfWeek.SUNDAY)
        days.addAll(dates.map { repository.getDay(it) ?: Day(date = it) })
        Timber.d(days.toString())
        return days.toList()
    }

    private fun getStartOfWeek(dateInNeededWeek: LocalDate): LocalDate {
        var startOfWeek = dateInNeededWeek
        do {
            startOfWeek = startOfWeek.minusDays(1)
        } while (startOfWeek.dayOfWeek != DayOfWeek.SUNDAY)
        return startOfWeek
    }
}