package com.natural.cycles.period.tracker.ui.articles.discover

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import timber.log.Timber
import com.natural.cycles.period.tracker.domain.usecase.articles.GetArticleGroupsUseCase
import com.natural.cycles.period.tracker.domain.usecase.articles.GetArticlesFlowUseCase
import com.natural.cycles.period.tracker.domain.usecase.articles.GetArticlesUseCase
import com.natural.cycles.period.tracker.ui.articles.ArticleItem
import com.natural.cycles.period.tracker.ui.articles.ArticlesEvent
import com.natural.cycles.period.tracker.preferences.RecentArticlesPreferences

class DiscoverViewModel(
    private val getArticleGroupsUseCase: GetArticleGroupsUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getArticlesFlowUseCase: GetArticlesFlowUseCase,
    private val recentArticlesPreferences: RecentArticlesPreferences
) : ViewModel() {
    val articlesStateFlow = getArticlesFlowUseCase.execute()
        .map { articles -> articles.map { ArticleItem.fromArticle(it) } }

    fun handleEvent(event: ArticlesEvent?) {

        event?.let {
            Timber.d("article: ${it.id}")
//            recentArticlesPreferences.putRecentArticle(it.id)
        }
    }

}