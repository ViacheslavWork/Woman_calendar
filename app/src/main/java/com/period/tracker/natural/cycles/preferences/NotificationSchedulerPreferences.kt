package com.period.tracker.natural.cycles.preferences

import android.content.Context
import org.koin.core.component.KoinComponent

class NotificationSchedulerPreferences(private val context: Context) : KoinComponent {
    companion object {
        private const val PREF_SCHEDULED = "scheduled"
        private const val PREF_SCHEDULED_FILE =
            "com.period.tracker.natural.cycles.utils.notification_scheduler"
    }

    fun isScheduled(): Boolean {
        val sharedPreferences =
            context.getSharedPreferences(PREF_SCHEDULED_FILE, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(PREF_SCHEDULED, false)
    }

    fun setIsScheduled(isScheduled: Boolean) {
        val sharedPreferences =
            context.getSharedPreferences(PREF_SCHEDULED_FILE, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(PREF_SCHEDULED, isScheduled).apply()
    }
}