package com.period.tracker.natural.cycles.data.database.days

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.period.tracker.natural.cycles.data.database.entity.DateConverters
import com.period.tracker.natural.cycles.data.database.entity.DayEntity
import com.period.tracker.natural.cycles.data.database.entity.SymptomsSetConverter

@Database(
    entities = [DayEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(DateConverters::class, SymptomsSetConverter::class)
abstract class DaysDatabase : RoomDatabase() {
    abstract val daysDao: DaysDao
}