package com.natural.cycles.period.tracker.preferences

import android.content.Context
import androidx.room.TypeConverter
import com.google.gson.Gson
import org.koin.core.component.KoinComponent
import org.threeten.bp.LocalDate
import timber.log.Timber

class EarliestPeriodPreferences(private val context: Context) : KoinComponent {
    companion object {
        private const val PREF_EARLIEST_PERIOD_START = "earliest_period_start"
        private const val PREF_EARLIEST_PERIOD_FILE =
            "com.natural.cycles.period.tracker.utils.earliest_period"
    }

    fun getStart(): LocalDate? {
        val sharedPreferences =
            context.getSharedPreferences(PREF_EARLIEST_PERIOD_FILE, Context.MODE_PRIVATE)
        val earliestPeriodDateStr = sharedPreferences.getString(PREF_EARLIEST_PERIOD_START, null)
        Timber.d(earliestPeriodDateStr?.let { jsonToLocalDate(it) }.toString())
        return earliestPeriodDateStr?.let { jsonToLocalDate(it) }
    }

    fun setStart(localDate: LocalDate) {
        Timber.d("localDate: $localDate")
        val sharedPreferences =
            context.getSharedPreferences(PREF_EARLIEST_PERIOD_FILE, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(PREF_EARLIEST_PERIOD_START, localDateToJson(localDate)).apply()
    }


    @TypeConverter
    private fun localDateToJson(value: LocalDate?): String = Gson().toJson(value)

    @TypeConverter
    private fun jsonToLocalDate(value: String?) =
        Gson().fromJson(value, LocalDate::class.java)
}