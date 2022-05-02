package woman.calendar.every.day.health.domain.usecase.articles

import woman.calendar.every.day.health.domain.ArticlesProvider
import woman.calendar.every.day.health.domain.model.Article

class GetArticleUseCase(val articlesProvider: ArticlesProvider) {
    fun execute(id: Int): Article? {
        return articlesProvider.getArticle(id)
    }
}