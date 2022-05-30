package com.period.tracker.natural.cycles.domain

import kotlinx.coroutines.flow.StateFlow
import com.period.tracker.natural.cycles.domain.model.Article

interface ArticlesProvider {
    fun getArticleGroups(): List<Article>
    fun getArticle(id: Int): Article?
    fun getArticles(): List<Article>
    fun getArticlesFlow(): StateFlow<List<Article>>
}