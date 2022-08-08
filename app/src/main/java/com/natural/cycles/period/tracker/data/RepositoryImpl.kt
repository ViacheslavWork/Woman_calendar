package com.natural.cycles.period.tracker.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.natural.cycles.period.tracker.data.database.days.DaysDao
import com.natural.cycles.period.tracker.data.database.entity.DayEntity
import com.natural.cycles.period.tracker.data.database.entity.toDay
import com.natural.cycles.period.tracker.domain.Repository
import com.natural.cycles.period.tracker.domain.model.Day
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import timber.log.Timber

class RepositoryImpl(private val daysDao: DaysDao) : Repository {
    private val mDays = mutableMapOf<LocalDate, Day>()
    private val database: DatabaseReference = Firebase.database.reference

    init {
//        GlobalScope.launch(Dispatchers.IO) { daysDao.deleteAll() }
    }

    override suspend fun setDays(days: List<Day>) {
        Timber.d("set days")
        daysDao.insertAll(days.map { DayEntity.fromDay(it) })
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