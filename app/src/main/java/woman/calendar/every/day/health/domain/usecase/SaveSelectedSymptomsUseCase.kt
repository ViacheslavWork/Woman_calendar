package woman.calendar.every.day.health.domain.usecase

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Symptom

class SaveSelectedSymptomsUseCase(val repository: Repository) {
    suspend fun execute(date: LocalDate, symptoms: Set<Symptom>) {
        val day = repository.getDay(date)
        day?.symptoms?.apply {
            clear()
            addAll(symptoms)
        }
    }
}