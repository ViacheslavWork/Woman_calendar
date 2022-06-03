package com.period.tracker.natural.cycles.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.period.tracker.natural.cycles.domain.model.Day

object DayConverter {
    @TypeConverter
    fun dayToJson(value: Day?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToDay(value: String) = Gson().fromJson(value, Day::class.java)
}