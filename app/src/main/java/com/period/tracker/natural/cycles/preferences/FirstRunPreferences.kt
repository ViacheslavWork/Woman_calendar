package com.period.tracker.natural.cycles.preferences

import android.content.Context
import org.koin.core.component.KoinComponent

class FirstRunPreferences(private val context: Context) : KoinComponent {
    companion object {
        private const val PREF_FIRST_RUN = "first_run"
        private const val PREF_FIRST_RUN_FILE =
            "com.period.tracker.natural.cycles.preferences.first_run"
    }

    fun isFirstRun(): Boolean {
        val sharedPreferences =
            context.getSharedPreferences(PREF_FIRST_RUN_FILE, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(PREF_FIRST_RUN, true)
    }

    fun setIsFirstRun(isFirstRun: Boolean) {
        val sharedPreferences =
            context.getSharedPreferences(PREF_FIRST_RUN_FILE, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(PREF_FIRST_RUN, isFirstRun).apply()
    }
}