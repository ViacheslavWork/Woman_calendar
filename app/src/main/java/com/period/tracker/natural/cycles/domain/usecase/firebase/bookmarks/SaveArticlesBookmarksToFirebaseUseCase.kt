package com.period.tracker.natural.cycles.domain.usecase.firebase.bookmarks

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.period.tracker.natural.cycles.utils.Constants

class SaveArticlesBookmarksToFirebaseUseCase(private val firebaseDatabase: DatabaseReference) {
    fun execute(bookmarksJson: String) {
        Firebase.auth.currentUser?.let { currentUser ->
            firebaseDatabase
                .child(Constants.FIREBASE_DATABASE_ARTICLES_BOOKMARKS_NAME)
                .child(currentUser.uid)
                .setValue(bookmarksJson)
        }
    }
}