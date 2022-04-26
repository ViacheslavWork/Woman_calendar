package woman.calendar.every.day.health.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import java.util.*

object LocalDateHelper {
    fun LocalDate.getMonthName() = this.month.toString()
        .lowercase(Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    fun Month.getName() = this.toString()
        .lowercase(Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    fun getByMonth(year: Int, month: Month): LocalDate {
        return LocalDate.of(year, month, 1)
    }
}