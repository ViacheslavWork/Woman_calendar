package woman.calendar.every.day.health.ui.articles.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import woman.calendar.every.day.health.domain.usecase.articles.GetArticlesFlowUseCase
import woman.calendar.every.day.health.ui.articles.ArticleItem
import woman.calendar.every.day.health.utils.RecentArticlesPreferences

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