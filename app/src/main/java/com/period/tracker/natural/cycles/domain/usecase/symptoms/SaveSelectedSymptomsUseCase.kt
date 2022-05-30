package com.period.tracker.natural.cycles.domain.usecase.symptoms

import org.threeten.bp.LocalDate
import com.period.tracker.natural.cycles.domain.Repository
import com.period.tracker.natural.cycles.domain.model.Symptom
import com.period.tracker.natural.cycles.domain.usecase.days.GetDayUseCase

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