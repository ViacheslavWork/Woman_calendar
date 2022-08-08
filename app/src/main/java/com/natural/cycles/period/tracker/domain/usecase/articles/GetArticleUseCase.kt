package com.natural.cycles.period.tracker.domain.usecase.articles

import com.natural.cycles.period.tracker.domain.ArticlesProvider
import com.natural.cycles.period.tracker.domain.model.Article

class GetArticleUseCase(val articlesProvider: ArticlesProvider) {
    fun execute(id: Int): Article? {
        return articlesProvider.getArticle(id)
    }
}