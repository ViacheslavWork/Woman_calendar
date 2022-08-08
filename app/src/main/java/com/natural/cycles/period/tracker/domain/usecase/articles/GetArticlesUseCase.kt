package com.natural.cycles.period.tracker.domain.usecase.articles

import com.natural.cycles.period.tracker.domain.ArticlesProvider
import com.natural.cycles.period.tracker.domain.model.Article

class GetArticlesUseCase(private val articlesProvider: ArticlesProvider) {
    fun execute(): List<Article> {
        return articlesProvider.getArticles()
    }
}