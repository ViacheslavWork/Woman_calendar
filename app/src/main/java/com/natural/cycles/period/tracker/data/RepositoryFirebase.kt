package com.natural.cycles.period.tracker.data

import androidx.room.TypeConverter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.natural.cycles.period.tracker.data.database.entity.DayEntity
import com.natural.cycles.period.tracker.domain.Repository
import com.natural.cycles.period.tracker.domain.model.Day
import org.threeten.bp.LocalDate

class RepositoryFirebase : Repository {
    private val database: DatabaseReference = Firebase.database.reference
    override suspend fun setDays(days: List<Day>) {
        TODO("Not yet implemented")
    }

    override suspend fun setDay(day: Day) {
        database
            .child("days")
            .child(DayEntity.fromDay(day).date.toString())
            .setValue(DayConverter.dayToJson(day))
    }

    override suspend fun getDay(date: LocalDate): Day? {
        return null
    }

    override suspend fun deleteDay(date: LocalDate) {

    }
}

object DayConverter {
    @TypeConverter
    fun dayToJson(value: Day?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToDay(value: String) = Gson().fromJson(value, Day::class.java)
}