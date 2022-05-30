package com.period.tracker.natural.cycles.ui.articles.discover

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import timber.log.Timber
import com.period.tracker.natural.cycles.domain.usecase.articles.GetArticleGroupsUseCase
import com.period.tracker.natural.cycles.domain.usecase.articles.GetArticlesFlowUseCase
import com.period.tracker.natural.cycles.domain.usecase.articles.GetArticlesUseCase
import com.period.tracker.natural.cycles.ui.articles.ArticleItem
import com.period.tracker.natural.cycles.ui.articles.ArticlesEvent
import com.period.tracker.natural.cycles.utils.RecentArticlesPreferences

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