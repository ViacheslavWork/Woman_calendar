package com.natural.cycles.period.tracker.data.database.days

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.natural.cycles.period.tracker.data.database.entity.DateConverters
import com.natural.cycles.period.tracker.data.database.entity.DayEntity
import com.natural.cycles.period.tracker.data.database.entity.SymptomsSetConverter

@Database(
    entities = [DayEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(DateConverters::class, SymptomsSetConverter::class)
abstract class DaysDatabase : RoomDatabase() {
    abstract val daysDao: DaysDao
}