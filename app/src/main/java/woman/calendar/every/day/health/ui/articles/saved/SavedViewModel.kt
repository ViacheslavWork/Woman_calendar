package woman.calendar.every.day.health.ui.articles.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import woman.calendar.every.day.health.domain.usecase.articles.GetArticlesFlowUseCase
import woman.calendar.every.day.health.ui.articles.ArticleItem
import woman.calendar.every.day.health.utils.BookmarksPreferences

class SavedViewModel(
    private val getArticlesFlowUseCase: GetArticlesFlowUseCase,
    private val bookmarksPreferences: BookmarksPreferences
) : ViewModel() {
    private val articlesStateFlow = getArticlesFlowUseCase.execute()
        .map { articles ->
            articles.map { ArticleItem.fromArticle(it) }
        }
    private val _bookmarkArticlesFlow = MutableStateFlow<List<ArticleItem>?>(null)
    val bookmarkArticlesFlow: StateFlow<List<ArticleItem>?> = _bookmarkArticlesFlow

    init {
        updateBookmarkArticles()
    }

    fun updateBookmarkArticles() {
        viewModelScope.launch {
            articlesStateFlow.collectLatest { list ->
                Timber.d(list.map { it.id }.toString())
                _bookmarkArticlesFlow.emit(
                    list.filter {
                        bookmarksPreferences.getBookmarks()?.contains(it.id) == true
                    }
                        .sortedBy { item ->
                            val orderById = bookmarksPreferences.getBookmarks()
                                ?.withIndex()
                                ?.associate { it.value to it.index }
                            orderById?.get(item.id)
                        })
            }
        }
    }

}