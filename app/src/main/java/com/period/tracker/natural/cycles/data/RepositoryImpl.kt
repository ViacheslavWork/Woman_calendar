package com.period.tracker.natural.cycles.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.data.database.entity.DayEntity
import com.period.tracker.natural.cycles.data.database.entity.toDay
import com.period.tracker.natural.cycles.data.database.days.DaysDao
import com.period.tracker.natural.cycles.domain.Repository
import com.period.tracker.natural.cycles.domain.model.Day

class RepositoryImpl(private val daysDao: DaysDao) : Repository {
    private val mDays = mutableMapOf<LocalDate, Day>()

    init {
//        GlobalScope.launch(Dispatchers.IO) { daysDao.deleteAll() }
    }

    override suspend fun setDay(day: Day) = withContext(Dispatchers.IO) {
//        mDays[day.date] = day
        daysDao.insert(DayEntity.fromDay(day))
    }

    override suspend fun getDay(date: LocalDate): Day? = withContext(Dispatchers.IO) {
//        return@withContext mDays[date]
        return@withContext daysDao.getDay(date)?.toDay()
    }

    override suspend fun deleteDay(date: LocalDate) = withContext(Dispatchers.IO) {
        daysDao.delete(date)
    }
}