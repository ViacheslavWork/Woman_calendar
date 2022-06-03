package com.period.tracker.natural.cycles.domain.usecase.days

import com.period.tracker.natural.cycles.domain.Repository
import com.period.tracker.natural.cycles.domain.model.Day
import com.period.tracker.natural.cycles.domain.usecase.firebase.days.SaveDayToFirebaseUseCase

class SaveDayUseCase(
    private val repository: Repository,
    private val saveDayToFirebaseUseCase: SaveDayToFirebaseUseCase
) {
    suspend fun execute(day: Day) {
        saveDayToFirebaseUseCase.execute(day)
        repository.setDay(day)
    }
}