package com.natural.cycles.period.tracker.data.database.days

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.natural.cycles.period.tracker.data.database.entity.DayEntity
import org.threeten.bp.LocalDate

@Dao
interface DaysDao {
    @Query("SELECT * FROM days")
    fun getDays(): List<DayEntity?>?

    @Query("SELECT * FROM days WHERE date=:date")
    fun getDay(date: LocalDate): DayEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dayEntity: DayEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dayEntities: List<DayEntity>)

    @Query("DELETE FROM days WHERE date=:date")
    fun delete(date: LocalDate)

    @Query("DELETE FROM days")
    fun deleteAll()
}