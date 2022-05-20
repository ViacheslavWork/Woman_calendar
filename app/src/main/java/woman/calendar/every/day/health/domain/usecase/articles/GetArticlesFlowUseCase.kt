package woman.calendar.every.day.health.domain.usecase.articles

import kotlinx.coroutines.flow.StateFlow
import woman.calendar.every.day.health.domain.ArticlesProvider
import woman.calendar.every.day.health.domain.model.Article

class GetArticlesFlowUseCase(private val articlesProvider: ArticlesProvider) {
    fun execute(): StateFlow<List<Article>> {
        return articlesProvider.getArticlesFlow()
    }
}