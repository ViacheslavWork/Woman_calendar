package com.natural.cycles.period.tracker.ui.symptoms

sealed class SymptomEvent(val symptom: SymptomItem) {
    class OnSymptomClick(symptom: SymptomItem) : SymptomEvent(symptom)
}
