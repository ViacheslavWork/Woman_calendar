package woman.calendar.every.day.health.utils

import timber.log.Timber
import java.util.*

object IdHelper {
    private var number = 0
    fun getId(): Int {
        number += 1
        Timber.d(number.toString())
        return number
//        return UUID.randomUUID().toString()
    }
}