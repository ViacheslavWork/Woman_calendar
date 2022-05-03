package woman.calendar.every.day.health.domain.usecase.symptoms

import org.threeten.bp.LocalDate
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.model.Symptom
import woman.calendar.every.day.health.domain.usecase.days.GetDayUseCase

class SaveSelectedSymptomsUseCase(val repository: Repository, val getDayUseCase: GetDayUseCase) {
    suspend fun execute(date: LocalDate, symptoms: Set<Symptom>) {
        val day = getDayUseCase.execute(date)
        day.symptoms.apply {
            clear()
            addAll(symptoms)
        }
        day.let { repository.setDay(it) }
    }
}