package com.period.tracker.natural.cycles.domain.usecase.symptoms

import com.period.tracker.natural.cycles.domain.model.Symptom
import com.period.tracker.natural.cycles.domain.usecase.days.GetDayUseCase
import com.period.tracker.natural.cycles.domain.usecase.days.SaveDayUseCase
import org.threeten.bp.LocalDate

class SaveSelectedSymptomsUseCase(
    private val saveDayUseCase: SaveDayUseCase,
    private val getDayUseCase: GetDayUseCase
) {
    suspend fun execute(date: LocalDate, symptoms: Set<Symptom>) {
        val day = getDayUseCase.execute(date)
        day.symptoms.apply {
            clear()
            addAll(symptoms)
        }
        saveDayUseCase.execute(day)
    }
}