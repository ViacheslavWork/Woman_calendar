package com.natural.cycles.period.tracker.domain.usecase.symptoms

import com.natural.cycles.period.tracker.domain.SymptomsProvider
import com.natural.cycles.period.tracker.domain.model.Symptom

class GetSymptomsUseCase(private val symptomsProvider: SymptomsProvider) {
    fun execute(): List<Symptom> {
        return symptomsProvider.getSymptoms()
    }
}