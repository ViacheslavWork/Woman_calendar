package woman.calendar.every.day.health.utils

import android.content.Context
import androidx.room.TypeConverter
import com.google.gson.Gson
import org.koin.core.component.KoinComponent
import org.threeten.bp.LocalDate
import timber.log.Timber

class LatestPeriodPreferences(private val context: Context) : KoinComponent {
    companion object {
        private const val PREF_LATEST_PERIOD_END = "LATEST_period_End"
        private const val PREF_LATEST_PERIOD_FILE =
            "woman.calendar.every.day.health.utils.LATEST_period"
    }

    fun getEnd(): LocalDate? {
        val sharedPreferences =
            context.getSharedPreferences(PREF_LATEST_PERIOD_FILE, Context.MODE_PRIVATE)
        val latestPeriodDateStr = sharedPreferences.getString(PREF_LATEST_PERIOD_END, null)
        Timber.d(latestPeriodDateStr?.let { jsonToLocalDate(it) }.toString())
        return latestPeriodDateStr?.let { jsonToLocalDate(it) }
    }

    fun setEnd(localDate: LocalDate) {
        Timber.d("localDate: $localDate")
        val sharedPreferences =
            context.getSharedPreferences(PREF_LATEST_PERIOD_FILE, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(PREF_LATEST_PERIOD_END, localDateToJson(localDate)).apply()
    }


    @TypeConverter
    private fun localDateToJson(value: LocalDate?): String = Gson().toJson(value)

    @TypeConverter
    private fun jsonToLocalDate(value: String?) =
        Gson().fromJson(value, LocalDate::class.java)
}