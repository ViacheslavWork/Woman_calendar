package com.period.tracker.natural.cycles.domain.usecase.symptoms

import com.period.tracker.natural.cycles.domain.SymptomsProvider
import com.period.tracker.natural.cycles.domain.model.Symptom

class GetSymptomsUseCase(private val symptomsProvider: SymptomsProvider) {
    fun execute(): List<Symptom> {
        return symptomsProvider.getSymptoms()
    }
}