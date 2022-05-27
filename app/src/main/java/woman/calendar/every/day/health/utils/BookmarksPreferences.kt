package woman.calendar.every.day.health.utils

import android.content.Context
import androidx.room.TypeConverter
import com.google.gson.Gson
import org.koin.core.component.KoinComponent
import timber.log.Timber

class BookmarksPreferences(private val context: Context) : KoinComponent {
    companion object {
        private const val PREF_BOOKMARKS = "bookmarks"
        private const val PREF_BOOKMARKS_FILE = "woman.calendar.every.day.health.utils.bookmarks"
    }

    fun getBookmarks(): Set<Int> {
        val sharedPreferences =
            context.getSharedPreferences(PREF_BOOKMARKS_FILE, Context.MODE_PRIVATE)
        val bookmarksStr = sharedPreferences.getString(PREF_BOOKMARKS, null)
        Timber.d(bookmarksStr?.let { jsonToSet(it) }.toString())
        return bookmarksStr?.let { jsonToSet(it) } ?: setOf()
    }

    fun putBookmark(articleId: Int) {
        Timber.d("bookmark id: $articleId")
        val sharedPreferences =
            context.getSharedPreferences(PREF_BOOKMARKS_FILE, Context.MODE_PRIVATE)
        val bookmarksStr = sharedPreferences.getString(PREF_BOOKMARKS, null)
        val bookmarks = bookmarksStr?.let { jsonToSet(it) } ?: mutableSetOf()
        bookmarks.add(articleId)
        sharedPreferences.edit().putString(PREF_BOOKMARKS, setToJson(bookmarks)).apply()
    }

    fun removeBookmark(articleId: Int) {
        val sharedPreferences =
            context.getSharedPreferences(PREF_BOOKMARKS_FILE, Context.MODE_PRIVATE)
        val bookmarksStr = sharedPreferences.getString(PREF_BOOKMARKS, null)
        val bookmarks = bookmarksStr?.let { jsonToSet(bookmarksStr) } ?: mutableSetOf()
        bookmarks.remove(articleId)
        sharedPreferences.edit().putString(PREF_BOOKMARKS, setToJson(bookmarks)).apply()
    }

    @TypeConverter
    private fun setToJson(value: Set<Int>?): String = Gson().toJson(value)

    @TypeConverter
    private fun jsonToSet(value: String) =
        Gson().fromJson(value, Array<Int>::class.java).toMutableSet()
}