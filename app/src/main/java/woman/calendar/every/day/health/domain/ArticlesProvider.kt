package woman.calendar.every.day.health.domain

import woman.calendar.every.day.health.domain.model.Article

interface ArticlesProvider {
    fun getArticleGroups(): List<Article>
    fun getArticle(id: Int): Article?
}