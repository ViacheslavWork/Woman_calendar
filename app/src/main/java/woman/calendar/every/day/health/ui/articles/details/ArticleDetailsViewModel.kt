package woman.calendar.every.day.health.ui.articles.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import woman.calendar.every.day.health.domain.model.Article
import woman.calendar.every.day.health.domain.usecase.articles.GetArticleUseCase
import woman.calendar.every.day.health.ui.articles.items.ArticleItem

class ArticleDetailsViewModel(private val getArticleUseCase: GetArticleUseCase) : ViewModel() {
    private val _article = MutableLiveData<Article?>()
    val article: LiveData<Article?> = _article

    private val _internalArticles = MutableLiveData<List<ArticleItem>?>(mutableListOf())
    val internalArticles: LiveData<List<ArticleItem>?> = _internalArticles

    fun getArticle(id: Int) {
        Timber.d(getArticleUseCase.execute(id).toString())
        _article.value = getArticleUseCase.execute(id)
        fillInternalArticles()
    }

    fun fillInternalArticles() {
        article.value?.internalArticlesId?.forEach {
            val internalArticles = internalArticles.value?.toMutableList()
            val article = getArticleUseCase.execute(it)
            internalArticles?.add( ArticleItem.fromArticleGroup(article!!))
            Timber.d(internalArticles.toString())
            _internalArticles.value = (internalArticles)
        }
    }
}