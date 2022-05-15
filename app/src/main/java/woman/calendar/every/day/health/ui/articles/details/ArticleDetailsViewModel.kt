package woman.calendar.every.day.health.ui.articles.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import woman.calendar.every.day.health.domain.model.Article
import woman.calendar.every.day.health.domain.usecase.articles.GetArticleUseCase
import woman.calendar.every.day.health.domain.usecase.articles.GetArticlesFlowUseCase
import woman.calendar.every.day.health.ui.articles.ArticleItem
import woman.calendar.every.day.health.utils.RecentArticlesPreferences
import java.util.*

class ArticleDetailsViewModel(
    private val getArticleUseCase: GetArticleUseCase,
    getArticlesFlowUseCase: GetArticlesFlowUseCase,
    private val recentArticlesPreferences: RecentArticlesPreferences
) : ViewModel() {
    private val _article = MutableLiveData<Article?>()
    val article: LiveData<Article?> = _article

    private var articlesStateFlow = getArticlesFlowUseCase.execute()
        .map { articles ->
            articles.map { ArticleItem.fromArticle(it) }
        }

    private val _internalArticlesFlow = MutableStateFlow<List<ArticleItem>?>(null)
    val internalArticlesFlow: StateFlow<List<ArticleItem>?> = _internalArticlesFlow

    private val articleIdStack: Stack<Int> = Stack()

    fun getArticle(id: Int) {
        recentArticlesPreferences.putRecentArticle(id)
        val article = getArticleUseCase.execute(id)
        _article.value = article
        updateInternalArticles(article)
    }

    private fun updateInternalArticles(article: Article?) {
        viewModelScope.launch {
            articlesStateFlow.collectLatest {
                _internalArticlesFlow.emit(it.filter { articleItem -> article?.type == articleItem.parentGroupType })
            }
        }
    }

    fun isArticleStackEmpty(): Boolean {
        return articleIdStack.isEmpty()
    }

    fun putCurrentArticleToStack(articleId: Int) {
        articleIdStack.push(articleId)
        Timber.d(articleIdStack.toString())
    }

    fun popParentArticle() {
        getArticle(articleIdStack.pop())
    }
}