package woman.calendar.every.day.health.ui.home

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.Day
import woman.calendar.every.day.health.domain.model.StateOfDay
import woman.calendar.every.day.health.ui.calendar.ItemDay

data class ItemDayOfWeek(
    val date: LocalDate,
    var stateOfDay: StateOfDay? = null,
) {
    val numOfDay: String = date.dayOfMonth.toString()
    val dayOfWeek: String = date.dayOfWeek.toString().first().toString()
    companion object {
        fun fromDay(day: Day): ItemDayOfWeek {
            return ItemDayOfWeek(
                date = day.date,
                stateOfDay = day.stateOfDay
            )
        }
    }
}