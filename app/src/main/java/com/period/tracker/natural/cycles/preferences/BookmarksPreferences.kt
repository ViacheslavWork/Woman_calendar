package com.period.tracker.natural.cycles.preferences

import android.content.Context
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.period.tracker.natural.cycles.domain.usecase.firebase.bookmarks.SaveArticlesBookmarksToFirebaseUseCase
import org.koin.core.component.KoinComponent
import timber.log.Timber

class BookmarksPreferences(
    private val context: Context,
    private val saveArticlesBookmarksToFirebaseUseCase: SaveArticlesBookmarksToFirebaseUseCase
) : KoinComponent {
    companion object {
        private const val PREF_BOOKMARKS = "bookmarks"
        private const val PREF_BOOKMARKS_FILE = "com.period.tracker.natural.cycles.utils.bookmarks"
    }

    private val sharedPreferences =
        context.getSharedPreferences(PREF_BOOKMARKS_FILE, Context.MODE_PRIVATE)

    fun getBookmarks(): Set<Int> {
        val bookmarksStr = sharedPreferences.getString(PREF_BOOKMARKS, null)
        Timber.d(bookmarksStr?.let { jsonToSet(it) }.toString())
        return bookmarksStr?.let { jsonToSet(it) } ?: setOf()
    }

    fun setBookmarks(bookmarksJson: String) {
        sharedPreferences.edit().putString(PREF_BOOKMARKS, bookmarksJson).apply()
    }

    fun putBookmark(articleId: Int) {
        Timber.d("bookmark id: $articleId")
        val bookmarks = getBookmarks().toMutableSet()
        bookmarks.add(articleId)
        setBookmarks(setToJson(bookmarks))

        saveArticlesBookmarksToFirebaseUseCase.execute(setToJson(bookmarks))
    }

    fun removeBookmark(articleId: Int) {
        val bookmarks = getBookmarks().toMutableSet()
        bookmarks.remove(articleId)
        setBookmarks(setToJson(bookmarks))

        saveArticlesBookmarksToFirebaseUseCase.execute(setToJson(bookmarks))
    }

    @TypeConverter
    private fun setToJson(value: Set<Int>?): String = Gson().toJson(value)

    @TypeConverter
    private fun jsonToSet(value: String) =
        Gson().fromJson(value, Array<Int>::class.java).toMutableSet()
}