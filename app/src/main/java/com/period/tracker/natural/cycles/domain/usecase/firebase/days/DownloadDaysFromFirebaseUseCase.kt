package com.period.tracker.natural.cycles.domain.usecase.firebase.days

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.period.tracker.natural.cycles.data.DayConverter
import com.period.tracker.natural.cycles.domain.Repository
import com.period.tracker.natural.cycles.utils.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

class DownloadDaysFromFirebaseUseCase(
    private val firebaseDatabaseReference: DatabaseReference,
    private val repository: Repository,
) {
    suspend fun execute() = withContext(Dispatchers.IO) {
        val isDaysAddedFlow = MutableStateFlow<Boolean?>(null)
        val scope = CoroutineScope(Dispatchers.IO)
        Firebase.auth.currentUser?.let {
            firebaseDatabaseReference
                .child(Constants.FIREBASE_DATABASE_DAYS_NAME)
                .child(it.uid)
                .get()
                .addOnSuccessListener { dataSnapshot ->
                    dataSnapshot.value?.let { value ->
                        val daysStr = value as HashMap<*, *>
                        scope.launch {
                            repository.setDays(daysStr.values.map { any ->
                                DayConverter.jsonToDay(
                                    any as String
                                )
                            })
                            isDaysAddedFlow.emit(true)
                            Timber.d("Days: added")
                            cancel()
                        }
                    }
                }.addOnFailureListener {
                    scope.launch {
                        isDaysAddedFlow.emit(false)
                        Timber.d("Days: failure")
                        cancel()
                    }
                }
        }
        return@withContext isDaysAddedFlow
    }
}