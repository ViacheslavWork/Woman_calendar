package com.natural.cycles.period.tracker.domain.usecase.articles

import kotlinx.coroutines.flow.StateFlow
import com.natural.cycles.period.tracker.domain.ArticlesProvider
import com.natural.cycles.period.tracker.domain.model.Article

class GetArticlesFlowUseCase(private val articlesProvider: ArticlesProvider) {
    fun execute(): StateFlow<List<Article>> {
        return articlesProvider.getArticlesFlow()
    }
}