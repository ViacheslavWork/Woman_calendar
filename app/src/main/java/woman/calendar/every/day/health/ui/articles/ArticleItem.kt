package woman.calendar.every.day.health.ui.articles

import android.net.Uri
import woman.calendar.every.day.health.domain.model.Article
import woman.calendar.every.day.health.domain.model.ArticleGroupType

data class ArticleItem(
    val id: Int,
    val title: String,
    val type: ArticleGroupType?,
    val parentGroupType: ArticleGroupType,
    val articlesId: List<Int>?,
    val bigImage: Uri,
    val smallImage: Uri
) {
    companion object {
        fun fromArticle(article: Article): ArticleItem {
            return ArticleItem(
                id = article.id,
                title = article.title,
                type = article.type,
                parentGroupType = article.parentGroupType,
                articlesId = article.internalArticlesId,
                bigImage = article.bigImage,
                smallImage = article.smallImage
            )
        }
    }
}