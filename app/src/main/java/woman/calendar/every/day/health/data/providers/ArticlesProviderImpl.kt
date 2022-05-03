package woman.calendar.every.day.health.data.providers

import android.content.Context
import woman.calendar.every.day.health.R
import woman.calendar.every.day.health.domain.ArticlesProvider
import woman.calendar.every.day.health.domain.model.Article
import woman.calendar.every.day.health.domain.model.ArticleGroupType.YOUR_CYCLE_PHASE
import woman.calendar.every.day.health.utils.IdHelper

class ArticlesProviderImpl(val context: Context) : ArticlesProvider {
    val articles = mutableMapOf<Int, Article>()

    override fun getArticleGroups(): List<Article> {
        var idHelper = IdHelper()
        val articles = mutableListOf<Article>()
        val internalArticles = mutableListOf<Article>()
        for (i in 0..5) {
            internalArticles.add(
                Article(
                    id = idHelper.getId(),
                    title = "Internal article $i",
                    content = context.resources.getString(R.string.text_long),
                    internalArticlesId = emptyList(),
                    type = YOUR_CYCLE_PHASE
                )
            )
        }
        for (i in 0..10) {
            articles.add(
                Article(
                    id = idHelper.getId(),
                    title = "Group $i",
                    content = context.resources.getString(R.string.text_long),
                    internalArticlesId = internalArticles.map { it.id },
                    type = YOUR_CYCLE_PHASE
                )
            )
        }
        this.articles.putAll(articles.associateBy { it.id })
        this.articles.putAll(internalArticles.associateBy { it.id })
        return articles.toList()
    }

    override fun getArticle(id: Int): Article? {
        return articles[id]
    }
}