package woman.calendar.every.day.health.utils

import android.content.Context
import androidx.room.TypeConverter
import com.google.gson.Gson
import org.koin.core.component.KoinComponent
import timber.log.Timber
import java.util.*

class RecentArticlesPreferences(private val context: Context) : KoinComponent {
    companion object {
        private const val PREF_RECENT_ARTICLES = "recent_articles"
        private const val PREF_RECENT_ARTICLES_FILE =
            "woman.calendar.every.day.health.utils.recent_articles"

    }

    fun getRecentArticles(): List<Int>? {
        val sharedPreferences =
            context.getSharedPreferences(PREF_RECENT_ARTICLES_FILE, Context.MODE_PRIVATE)
        val bookmarksStr = sharedPreferences.getString(PREF_RECENT_ARTICLES, null)
        Timber.d(bookmarksStr?.let { jsonToList(bookmarksStr) }.toString())
        return bookmarksStr?.let { jsonToList(bookmarksStr) }
    }

    fun putRecentArticle(articleId: Int) {
        Timber.d("recent id: $articleId")
        val sharedPreferences =
            context.getSharedPreferences(PREF_RECENT_ARTICLES_FILE, Context.MODE_PRIVATE)
        val recentArticlesStr = sharedPreferences.getString(PREF_RECENT_ARTICLES, null)
        val recentArticlesList = recentArticlesStr?.let { jsonToList(recentArticlesStr) }
            ?: mutableListOf()
        recentArticlesList.remove(articleId)
        recentArticlesList.add(0, articleId)
        while (recentArticlesList.size > Constants.NUMBER_OF_RECENT_ARTICLES) {
            recentArticlesList.removeAt(recentArticlesList.size - 1)
        }
        sharedPreferences.edit().putString(PREF_RECENT_ARTICLES, listToJson(recentArticlesList))
            .apply()
    }

    fun removeRecentArticle(articleId: Int) {
        val sharedPreferences =
            context.getSharedPreferences(PREF_RECENT_ARTICLES_FILE, Context.MODE_PRIVATE)
        val recentArticlesStr = sharedPreferences.getString(PREF_RECENT_ARTICLES, null)
        val recentArticlesQueue = recentArticlesStr?.let { jsonToList(recentArticlesStr) }
            ?: mutableListOf()
        recentArticlesQueue.remove(articleId)
        sharedPreferences.edit().putString(PREF_RECENT_ARTICLES, listToJson(recentArticlesQueue))
            .apply()
    }

    @TypeConverter
    private fun listToJson(value: List<Int>?): String = Gson().toJson(value)

    @TypeConverter
    private fun jsonToList(value: String): MutableList<Int> =
        Gson().fromJson(value, Array<Int>::class.java).toMutableList()
}