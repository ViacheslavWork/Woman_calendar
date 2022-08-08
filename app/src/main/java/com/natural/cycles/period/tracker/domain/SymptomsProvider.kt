package com.natural.cycles.period.tracker.domain

import com.natural.cycles.period.tracker.domain.model.Symptom

interface SymptomsProvider {
    fun getSymptoms(): List<Symptom>
}