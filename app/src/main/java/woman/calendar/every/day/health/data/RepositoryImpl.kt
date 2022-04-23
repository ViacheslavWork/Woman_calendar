package woman.calendar.every.day.health.data

import org.threeten.bp.LocalDate
import timber.log.Timber
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Day

class RepositoryImpl : Repository {
    private val mDays = mutableMapOf<LocalDate, Day>()

    override fun setDay(day: Day) {
        mDays[day.date] = day
    }

    override fun getDay(date: LocalDate): Day? {
        return mDays[date]
    }
}