package woman.calendar.every.day.health.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.Day
import woman.calendar.every.day.health.domain.model.StateOfDay


@Entity(tableName = "days")
data class DayEntity(
    @PrimaryKey
    @TypeConverters(DateConverters::class)
    val date: LocalDate,
    var stateOfDay: StateOfDay? = null
) {
    companion object {
        fun fromDay(day: Day): DayEntity {
            return DayEntity(
                date = day.date,
                stateOfDay = day.stateOfDay
            )
        }
    }
}

fun DayEntity.toDay(): Day {
    return Day(
        date = date,
        stateOfDay = stateOfDay
    )
}

class DateConverters {
    @TypeConverter
    fun fromString(value: String?): LocalDate? {
        return if (value == null) null else LocalDate.parse(value)
    }

    @TypeConverter
    fun toString(date: LocalDate?): String? {
        return date?.toString()
    }
}
