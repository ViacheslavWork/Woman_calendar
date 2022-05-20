package woman.calendar.every.day.health.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.Day
import woman.calendar.every.day.health.domain.model.StateOfDay
import woman.calendar.every.day.health.domain.model.Symptom

@Entity(tableName = "days")
data class DayEntity(
    @PrimaryKey
    @TypeConverters(DateConverters::class)
    val date: LocalDate,
    val stateOfDay: StateOfDay? = null,
    @TypeConverters(SymptomsSetConverter::class)
    val symptoms: MutableSet<Symptom>,
    val volumeOfWater: Float = 0F,
    val notes: String?,
) {
    companion object {
        fun fromDay(day: Day): DayEntity {
            return DayEntity(
                date = day.date,
                stateOfDay = day.stateOfDay,
                symptoms = day.symptoms,
                volumeOfWater = day.volumeOfWater,
                notes = day.notes
            )
        }
    }
}

fun DayEntity.toDay(): Day {
    return Day(
        date = date,
        stateOfDay = stateOfDay,
        symptoms = symptoms,
        volumeOfWater = volumeOfWater,
        notes = notes
    )
}

class SymptomsSetConverter {
    @TypeConverter
    fun setToJson(value: Set<Symptom>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToSet(value: String) = Gson().fromJson(value, Array<Symptom>::class.java).toMutableSet()
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
