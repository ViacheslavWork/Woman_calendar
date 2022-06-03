package com.period.tracker.natural.cycles.domain.usecase.firebase.bookmarks

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.period.tracker.natural.cycles.preferences.BookmarksPreferences
import com.period.tracker.natural.cycles.utils.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

class DownloadArticlesBookmarksFromFirebaseUseCase(
    private val firebaseDatabaseReference: DatabaseReference,
    private val bookmarksPreferences: BookmarksPreferences,
) {
    suspend fun execute() = withContext(Dispatchers.IO) {
        val isBookmarksAddedFlow = MutableStateFlow<Boolean?>(null)
        val scope = CoroutineScope(Dispatchers.IO)
        Firebase.auth.currentUser?.let {
            firebaseDatabaseReference
                .child(Constants.FIREBASE_DATABASE_ARTICLES_BOOKMARKS_NAME)
                .child(it.uid)
                .get()
                .addOnSuccessListener { dataSnapshot ->
                    dataSnapshot.value?.let { value ->
                        val bookmarksStr = value as String
                        scope.launch {
                            bookmarksPreferences.setBookmarks(bookmarksStr)
                            isBookmarksAddedFlow.emit(true)
                            Timber.d("Bookmarks: added = $bookmarksStr")
                            cancel()
                        }
                    }
                }.addOnFailureListener {
                    scope.launch {
                        isBookmarksAddedFlow.emit(false)
                        Timber.d("Bookmarks: failure")
                        cancel()
                    }
                }
        }
        return@withContext isBookmarksAddedFlow
    }
}