package com.natural.cycles.period.tracker.domain.usecase.days

import com.natural.cycles.period.tracker.domain.Repository
import com.natural.cycles.period.tracker.domain.model.Day
import com.natural.cycles.period.tracker.domain.usecase.firebase.days.SaveDayToFirebaseUseCase

class SaveDayUseCase(
    private val repository: Repository,
    private val saveDayToFirebaseUseCase: SaveDayToFirebaseUseCase
) {
    suspend fun execute(day: Day) {
        saveDayToFirebaseUseCase.execute(day)
        repository.setDay(day)
    }
}