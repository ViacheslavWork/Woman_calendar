package com.natural.cycles.period.tracker.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.natural.cycles.period.tracker.domain.model.Day

object DayConverter {
    @TypeConverter
    fun dayToJson(value: Day?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToDay(value: String) = Gson().fromJson(value, Day::class.java)
}