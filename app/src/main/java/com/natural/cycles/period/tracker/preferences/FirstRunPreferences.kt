package com.natural.cycles.period.tracker.preferences

import android.content.Context
import org.koin.core.component.KoinComponent

class FirstRunPreferences(context: Context) : KoinComponent {
    companion object {
        private const val PREF_FIRST_RUN = "first_run"
        private const val PREF_FIRST_RUN_FILE =
            "com.natural.cycles.period.tracker.preferences.first_run"
    }

    private val sharedPreferences =
        context.getSharedPreferences(PREF_FIRST_RUN_FILE, Context.MODE_PRIVATE)

    fun isFirstRun(): Boolean {
        return sharedPreferences.getBoolean(PREF_FIRST_RUN, true)
    }

    fun setIsFirstRun(isFirstRun: Boolean) {
        sharedPreferences.edit().putBoolean(PREF_FIRST_RUN, isFirstRun).apply()
    }
}