package woman.calendar.every.day.health.data.database.mixes

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woman.calendar.every.day.health.data.database.entity.DateConverters
import woman.calendar.every.day.health.data.database.entity.DayEntity

@Database(
    entities = [DayEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverters::class)
abstract class DaysDatabase : RoomDatabase() {
    abstract val daysDao: DaysDao
}