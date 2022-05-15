package woman.calendar.every.day.health.domain

import kotlinx.coroutines.flow.StateFlow
import woman.calendar.every.day.health.domain.model.Article

interface ArticlesProvider {
    fun getArticleGroups(): List<Article>
    fun getArticle(id: Int): Article?
    fun getArticles(): List<Article>
    fun getArticlesFlow(): StateFlow<List<Article>>
}