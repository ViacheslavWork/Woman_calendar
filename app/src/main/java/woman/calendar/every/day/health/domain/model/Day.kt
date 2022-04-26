package woman.calendar.every.day.health.domain.model

import org.threeten.bp.LocalDate

data class Day(val date: LocalDate, var stateOfDay: StateOfDay? = null)

enum class StateOfDay {
    PRE_PERIOD, PERIOD, FERTILE, EXPECTED_NEW_PERIOD, DELAY, OVULATION
}

