package woman.calendar.every.day.health.ui.symptoms

import woman.calendar.every.day.health.domain.model.Symptom

sealed class SymptomEvent(val symptom: SymptomItem) {
    class OnSymptomClick(symptom: SymptomItem) : SymptomEvent(symptom)
}
