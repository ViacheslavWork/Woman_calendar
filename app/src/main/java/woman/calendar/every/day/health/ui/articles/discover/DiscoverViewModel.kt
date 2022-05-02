package woman.calendar.every.day.health.ui.articles.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woman.calendar.every.day.health.domain.usecase.articles.GetArticleGroupsUseCase
import woman.calendar.every.day.health.ui.articles.items.ArticleItem
import woman.calendar.every.day.health.ui.articles.ArticlesEvent

class DiscoverViewModel(val getArticleGroupsUseCase: GetArticleGroupsUseCase) : ViewModel() {
    fun handleEvent(event: ArticlesEvent?) {
        //TODO("Not yet implemented")
    }

    private val _articleGroups = MutableLiveData<List<ArticleItem>>()
    val articleGroups: LiveData<List<ArticleItem>> = _articleGroups

    init {
        _articleGroups.postValue(
            getArticleGroupsUseCase.execute().map { ArticleItem.fromArticleGroup(it) })
    }
}