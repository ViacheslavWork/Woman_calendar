package com.period.tracker.natural.cycles.domain.usecase.articles

import com.period.tracker.natural.cycles.domain.ArticlesProvider
import com.period.tracker.natural.cycles.domain.model.Article

class GetArticleUseCase(val articlesProvider: ArticlesProvider) {
    fun execute(id: Int): Article? {
        return articlesProvider.getArticle(id)
    }
}