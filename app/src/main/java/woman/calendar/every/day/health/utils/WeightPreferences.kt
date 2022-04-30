package woman.calendar.every.day.health.utils

import android.content.Context

object WeightPreferences {
    private const val WEIGHT_IS_NOT_SETTLED = -1
    private const val PREF_WEIGHT = "weight"
    private const val PREF_WEIGHT_FILE = "woman.calendar.every.day.health.utils.weight"
    fun getWeight(context: Context): Int? {
        val sharedPreferences = context.getSharedPreferences(PREF_WEIGHT_FILE, Context.MODE_PRIVATE)
        val weight = sharedPreferences.getInt(PREF_WEIGHT, WEIGHT_IS_NOT_SETTLED)
        return if (weight == WEIGHT_IS_NOT_SETTLED) null
        else weight
    }

    fun setWeight(weight: Int, context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_WEIGHT_FILE, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(PREF_WEIGHT, weight).apply()
    }
}