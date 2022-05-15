package woman.calendar.every.day.health.ui.articles.discover

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import timber.log.Timber
import woman.calendar.every.day.health.domain.usecase.articles.GetArticleGroupsUseCase
import woman.calendar.every.day.health.domain.usecase.articles.GetArticlesFlowUseCase
import woman.calendar.every.day.health.domain.usecase.articles.GetArticlesUseCase
import woman.calendar.every.day.health.ui.articles.ArticleItem
import woman.calendar.every.day.health.ui.articles.ArticlesEvent
import woman.calendar.every.day.health.utils.RecentArticlesPreferences

class DiscoverViewModel(
    private val getArticleGroupsUseCase: GetArticleGroupsUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getArticlesFlowUseCase: GetArticlesFlowUseCase,
    private val recentArticlesPreferences: RecentArticlesPreferences
) : ViewModel() {
    val articlesStateFlow = getArticlesFlowUseCase.execute()
        .map { articles -> articles.map { ArticleItem.fromArticle(it) } }

    fun handleEvent(event: ArticlesEvent?) {

        event?.let {
            Timber.d("article: ${it.id}")
//            recentArticlesPreferences.putRecentArticle(it.id)
        }
    }

}