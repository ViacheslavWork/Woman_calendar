package com.natural.cycles.period.tracker.ui.articles

import android.net.Uri
import com.natural.cycles.period.tracker.domain.model.Article
import com.natural.cycles.period.tracker.domain.model.ArticleTitleColor
import com.natural.cycles.period.tracker.domain.model.ArticleType

data class ArticleItem(
    val id: Int,
    val title: String,
    val smallTitle: String?,
    val titleColor: ArticleTitleColor,
    val type: ArticleType?,
    val parentType: ArticleType,
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
                bigImage = article.bigImage,
                smallImage = article.smallImage
            )
        }
    }
}