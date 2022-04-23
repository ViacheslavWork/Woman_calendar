package woman.calendar.every.day.health.domain.model

import org.threeten.bp.LocalDate
import timber.log.Timber

data class Period(val start: LocalDate, val finish: LocalDate) {
    fun getLengthDays(): Int {
        var length = 0
        var tempDate = start
        while (tempDate <= finish) {
            length++
            tempDate = tempDate.plusDays(1)
        }
        Timber.d(length.toString())
        return length
    }
}