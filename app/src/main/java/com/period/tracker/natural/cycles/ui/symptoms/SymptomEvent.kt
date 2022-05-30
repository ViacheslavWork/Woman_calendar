package com.period.tracker.natural.cycles.ui.symptoms

sealed class SymptomEvent(val symptom: SymptomItem) {
    class OnSymptomClick(symptom: SymptomItem) : SymptomEvent(symptom)
}
