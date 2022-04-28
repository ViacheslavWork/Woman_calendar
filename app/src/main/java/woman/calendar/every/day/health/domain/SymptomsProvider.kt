package woman.calendar.every.day.health.domain

import woman.calendar.every.day.health.domain.model.Symptom

interface SymptomsProvider {
    fun getSymptoms(): List<Symptom>
}