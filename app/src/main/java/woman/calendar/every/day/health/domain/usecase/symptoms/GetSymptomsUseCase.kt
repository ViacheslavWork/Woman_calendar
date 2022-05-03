package woman.calendar.every.day.health.domain.usecase.symptoms

import woman.calendar.every.day.health.domain.SymptomsProvider
import woman.calendar.every.day.health.domain.model.Symptom

class GetSymptomsUseCase(private val symptomsProvider: SymptomsProvider) {
    fun execute(): List<Symptom> {
        return symptomsProvider.getSymptoms()
    }
}