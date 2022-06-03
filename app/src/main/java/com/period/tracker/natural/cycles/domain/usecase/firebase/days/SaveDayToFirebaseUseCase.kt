package com.period.tracker.natural.cycles.domain.usecase.firebase.days

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.period.tracker.natural.cycles.data.DayConverter
import com.period.tracker.natural.cycles.data.database.entity.DayEntity
import com.period.tracker.natural.cycles.domain.model.Day
import com.period.tracker.natural.cycles.utils.Constants

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