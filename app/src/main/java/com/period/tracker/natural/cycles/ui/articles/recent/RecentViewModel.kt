package com.period.tracker.natural.cycles.ui.articles.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.period.tracker.natural.cycles.domain.usecase.articles.GetArticlesFlowUseCase
import com.period.tracker.natural.cycles.ui.articles.ArticleItem
import com.period.tracker.natural.cycles.preferences.RecentArticlesPreferences

class RecentViewModel(
    private val getArticlesFlowUseCase: GetArticlesFlowUseCase,
    private val recentArticlesPreferences: RecentArticlesPreferences
) : ViewModel() {
    private val articlesStateFlow = getArticlesFlowUseCase.execute()
        .map { articles ->
            articles.map { ArticleItem.fromArticle(it) }
        }
    private val _recentArticlesFlow = MutableStateFlow<List<ArticleItem>?>(null)
    val recentArticlesFlow: StateFlow<List<ArticleItem>?> = _recentArticlesFlow

    init {
        updateRecentArticles()
    }

    fun updateRecentArticles() {
        viewModelScope.launch {
            articlesStateFlow.collectLatest { list ->
                _recentArticlesFlow.emit(
                    list.filter { item ->
                        recentArticlesPreferences.getRecentArticles()?.contains(item.id) == true
                    }
                        .sortedBy { item ->
                            val orderById = recentArticlesPreferences.getRecentArticles()
                                ?.withIndex()
                                ?.associate { it.value to it.index }
                            orderById?.get(item.id)
                        }
                )
            }
        }
    }

}