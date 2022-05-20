package woman.calendar.every.day.health.data.database.days

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woman.calendar.every.day.health.data.database.entity.DateConverters
import woman.calendar.every.day.health.data.database.entity.DayEntity
import woman.calendar.every.day.health.data.database.entity.SymptomsSetConverter

@Database(
    entities = [DayEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(DateConverters::class, SymptomsSetConverter::class)
abstract class DaysDatabase : RoomDatabase() {
    abstract val daysDao: DaysDao
}