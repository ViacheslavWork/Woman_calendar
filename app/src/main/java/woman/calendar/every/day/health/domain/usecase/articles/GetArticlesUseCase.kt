package woman.calendar.every.day.health.domain.usecase.articles

import woman.calendar.every.day.health.domain.ArticlesProvider
import woman.calendar.every.day.health.domain.model.Article
import woman.calendar.every.day.health.domain.model.ArticleGroupType

class GetArticlesUseCase(private val articlesProvider: ArticlesProvider) {
    fun execute(): List<Article> {
        return articlesProvider.getArticles()
    }
}