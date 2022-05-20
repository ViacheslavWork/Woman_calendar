package woman.calendar.every.day.health.ui.articles

import android.net.Uri
import woman.calendar.every.day.health.domain.model.Article
import woman.calendar.every.day.health.domain.model.ArticleTitleColor
import woman.calendar.every.day.health.domain.model.ArticleType

data class ArticleItem(
    val id: Int,
    val title: String,
    val smallTitle: String?,
    val titleColor: ArticleTitleColor,
    val type: ArticleType?,
    val parentType: ArticleType,
    val articlesId: List<Int>?,
    val bigImage: Uri,
    val smallImage: Uri
) {
    companion object {
        fun fromArticle(article: Article): ArticleItem {
            return ArticleItem(
                id = article.id,
                title = article.title,
                smallTitle = article.smallTitle,
                titleColor = article.titleColor,
                type = article.type,
                parentType = article.parentType,
                articlesId = article.internalArticlesId,
                bigImage = article.bigImage,
                smallImage = article.smallImage
            )
        }
    }
}