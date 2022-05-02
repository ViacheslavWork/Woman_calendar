package woman.calendar.every.day.health.ui.articles.items

import woman.calendar.every.day.health.domain.model.Article
import woman.calendar.every.day.health.domain.model.ArticleGroupType

data class ArticleItem(
    val id: Int,
    val title: String,
    val type: ArticleGroupType?,
    val articlesId: List<Int>?
) {
    companion object {
        fun fromArticleGroup(article: Article): ArticleItem {
            return ArticleItem(
                id = article.id,
                title = article.title,
                type = article.type,
                articlesId = article.internalArticlesId
            )
        }
    }
}