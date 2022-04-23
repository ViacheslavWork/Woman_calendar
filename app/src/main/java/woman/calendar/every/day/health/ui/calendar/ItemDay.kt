package woman.calendar.every.day.health.ui.calendar

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.model.Day
import woman.calendar.every.day.health.domain.model.StateOfDay

data class ItemDay(
    val date: LocalDate? = null,
    var stateOfDay: StateOfDay? = null,
) {
    val numOfDay: String? = date?.dayOfMonth?.toString()
    companion object {
        fun fromDay(day: Day): ItemDay {
            return ItemDay(
                date = day.date,
                stateOfDay = day.stateOfDay
            )
        }
    }
}