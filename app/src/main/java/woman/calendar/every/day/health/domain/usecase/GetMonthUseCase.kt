package woman.calendar.every.day.health.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import timber.log.Timber
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Day

class GetMonthUseCase(val repository: Repository) {
    suspend fun execute(dateInNeededMonth: LocalDate): List<Day> = withContext(Dispatchers.IO) {
        return@withContext getMonth(dateInNeededMonth)
    }

    private suspend fun getMonth(dateInNeededMonth: LocalDate): List<Day> {
        Timber.d(dateInNeededMonth.toString())
        val start = System.currentTimeMillis()
        var dayInMonth = LocalDate.of(dateInNeededMonth.year, dateInNeededMonth.month, 1)
        val dates = mutableListOf<LocalDate>()
        val days = mutableListOf<Day>()
        while (dayInMonth.month == dateInNeededMonth.month) {
            dates.add(dayInMonth)
            dayInMonth = dayInMonth.plusDays(1)
        }
        days.addAll(dates.map { repository.getDay(it) ?: Day(date = it) })
//        Timber.d("Get month time: ${System.currentTimeMillis() - start}")
        return days.toList()
    }
}