package com.natural.cycles.period.tracker.preferences

import android.content.Context
import android.preference.PreferenceManager
import org.threeten.bp.LocalDate

object PeriodPreferences {
    private const val PREF_FIRST_PERIOD = "first_period"
    private fun getFirstPeriodDate(context: Context): LocalDate? {
        return LocalDate.parse(
            PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_FIRST_PERIOD, null)
        )
    }

    fun setFirstPeriodDate(date: LocalDate, context: Context) {
        if (date.isBefore(getFirstPeriodDate(context))) {
            PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_FIRST_PERIOD, date.toString())
                .apply()
        }
    }
}