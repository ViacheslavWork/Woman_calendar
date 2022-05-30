package com.period.tracker.natural.cycles.domain.usecase.articles

import com.period.tracker.natural.cycles.domain.ArticlesProvider
import com.period.tracker.natural.cycles.domain.model.Article

class GetArticlesUseCase(private val articlesProvider: ArticlesProvider) {
    fun execute(): List<Article> {
        return articlesProvider.getArticles()
    }
}