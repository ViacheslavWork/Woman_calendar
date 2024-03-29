package com.natural.cycles.period.tracker.ui.articles.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.natural.cycles.period.tracker.domain.usecase.articles.GetArticlesFlowUseCase
import com.natural.cycles.period.tracker.ui.articles.ArticleItem
import com.natural.cycles.period.tracker.preferences.BookmarksPreferences

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
                _bookmarkArticlesFlow.emit(
                    list.filter { bookmarksPreferences.getBookmarks().contains(it.id) }
                        .sortedBy { item ->
                            val orderById = bookmarksPreferences.getBookmarks()
                                .withIndex()
                                .associate { it.value to it.index }
                            orderById[item.id]
                        })
            }
        }
    }

}