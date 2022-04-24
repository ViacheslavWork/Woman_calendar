package woman.calendar.every.day.health.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Day

class RepositoryTest() : Repository {
    private val mDays = mutableMapOf<LocalDate, Day>()

    override suspend fun setDay(day: Day) = withContext(Dispatchers.IO) {
        mDays[day.date] = day
    }

    override suspend fun getDay(date: LocalDate): Day? = withContext(Dispatchers.IO) {
        return@withContext mDays[date]
    }
}