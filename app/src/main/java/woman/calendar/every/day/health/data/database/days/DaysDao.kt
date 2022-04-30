package woman.calendar.every.day.health.data.database.days

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.data.database.entity.DayEntity

@Dao
interface DaysDao {
    @Query("SELECT * FROM days WHERE date=:date")
    fun getDay(date: LocalDate): DayEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dayEntity: DayEntity)

    @Query("DELETE FROM days WHERE date=:date")
    fun delete(date: LocalDate)

    @Query("DELETE FROM days")
    fun deleteAll()
}