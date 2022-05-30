package com.period.tracker.natural.cycles.domain.model

import org.threeten.bp.LocalDate

data class Day(
    val date: LocalDate,
    var stateOfDay: StateOfDay? = null,
    val symptoms: MutableSet<Symptom> = mutableSetOf<Symptom>(),
    var volumeOfWater: Float = 0F,
    var notes: String? = null
)

enum class StateOfDay {
    PRE_PERIOD, PERIOD, FERTILE, EXPECTED_NEW_PERIOD, DELAY, OVULATION
}

