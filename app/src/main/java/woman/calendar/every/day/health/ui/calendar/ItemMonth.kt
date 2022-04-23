package woman.calendar.every.day.health.ui.calendar

import org.threeten.bp.LocalDate
import java.util.*

data class ItemMonth(val date: LocalDate, private val days: List<ItemDay>) {
    val title: String = "${getMonthName(date)} ${date.year}"
    val daysWithStartDelay = getDaysWithStartDelays()

    private fun getDaysWithStartDelays(): List<ItemDay> {
        val itemsDay = mutableListOf<ItemDay>().apply { addAll(days) }
        val firstWeekDayOfMonth = days[0].date!!.dayOfWeek.value
        for (i in 1 until firstWeekDayOfMonth) {
            itemsDay.add(0, ItemDay())
        }
        return itemsDay
    }

    private fun getMonthName(dateTemp: LocalDate) = dateTemp.month.toString()
        .lowercase(Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}