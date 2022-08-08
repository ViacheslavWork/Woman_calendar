package com.natural.cycles.period.tracker.domain.usecase.firebase.days

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.natural.cycles.period.tracker.data.DayConverter
import com.natural.cycles.period.tracker.data.database.entity.DayEntity
import com.natural.cycles.period.tracker.domain.model.Day
import com.natural.cycles.period.tracker.utils.Constants

class SaveDayToFirebaseUseCase(private val firebaseDatabase: DatabaseReference) {
    fun execute(day: Day) {
        Firebase.auth.currentUser?.let { currentUser ->
            firebaseDatabase
                .child(Constants.FIREBASE_DATABASE_DAYS_NAME)
                .child(currentUser.uid)
                .child(DayEntity.fromDay(day).date.toString())
                .setValue(DayConverter.dayToJson(day))
        }
    }
}