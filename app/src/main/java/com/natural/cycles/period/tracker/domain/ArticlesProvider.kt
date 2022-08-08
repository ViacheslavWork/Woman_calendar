package com.natural.cycles.period.tracker.domain

import kotlinx.coroutines.flow.StateFlow
import com.natural.cycles.period.tracker.domain.model.Article

interface ArticlesProvider {
    fun getArticleGroups(): List<Article>
    fun getArticle(id: Int): Article?
    fun getArticles(): List<Article>
    fun getArticlesFlow(): StateFlow<List<Article>>
}