package com.natural.cycles.period.tracker.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import com.natural.cycles.period.tracker.domain.Repository
import com.natural.cycles.period.tracker.domain.model.Day

class RepositoryTest() : Repository {
    private val mDays = mutableMapOf<LocalDate, Day>()
    override suspend fun setDays(days: List<Day>) {
        TODO("Not yet implemented")
    }

    override suspend fun setDay(day: Day) = withContext(Dispatchers.IO) {
        mDays[day.date] = day
//        Timber.d(mDays.toString())
    }

    override suspend fun getDay(date: LocalDate): Day? = withContext(Dispatchers.IO) {
        return@withContext mDays[date]
    }

    override suspend fun deleteDay(date: LocalDate) {
        mDays.remove(date)
    }
}