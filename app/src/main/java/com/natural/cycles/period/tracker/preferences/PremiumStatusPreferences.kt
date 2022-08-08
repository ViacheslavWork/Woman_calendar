package com.natural.cycles.period.tracker.preferences

import android.content.Context

class PremiumStatusPreferences(context: Context) {
    companion object {
        private const val PREF_IS_PREMIUM = "is_premium"
        private const val PREF_PREMIUM_FILE =
            "com.natural.cycles.period.tracker.preferences.premium_status"
    }

    private val sharedPreferences =
        context.getSharedPreferences(PREF_PREMIUM_FILE, Context.MODE_PRIVATE)

    fun userHasPremiumStatus(): Boolean {
        return sharedPreferences.getBoolean(PREF_IS_PREMIUM, false)
    }

    fun setPremiumStatus(isPremium: Boolean) {
        sharedPreferences
            .edit()
            .putBoolean(PREF_IS_PREMIUM, isPremium)
            .apply()
    }
}