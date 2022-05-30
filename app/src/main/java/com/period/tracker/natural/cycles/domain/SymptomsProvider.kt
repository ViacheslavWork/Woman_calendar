package com.period.tracker.natural.cycles.domain

import com.period.tracker.natural.cycles.domain.model.Symptom

interface SymptomsProvider {
    fun getSymptoms(): List<Symptom>
}