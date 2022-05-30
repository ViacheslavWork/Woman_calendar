package com.period.tracker.natural.cycles.domain.usecase.articles

import com.period.tracker.natural.cycles.domain.ArticlesProvider
import com.period.tracker.natural.cycles.domain.model.Article

class GetArticleGroupsUseCase(val articlesProvider: ArticlesProvider) {
    fun execute(): List<Article> {
        return articlesProvider.getArticleGroups()
    }
}