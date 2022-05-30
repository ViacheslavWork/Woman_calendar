package com.period.tracker.natural.cycles.domain.usecase.articles

import kotlinx.coroutines.flow.StateFlow
import com.period.tracker.natural.cycles.domain.ArticlesProvider
import com.period.tracker.natural.cycles.domain.model.Article

class GetArticlesFlowUseCase(private val articlesProvider: ArticlesProvider) {
    fun execute(): StateFlow<List<Article>> {
        return articlesProvider.getArticlesFlow()
    }
}