package com.period.tracker.natural.cycles.domain.usecase.firebase.articles

import com.google.firebase.database.DatabaseReference
import com.period.tracker.natural.cycles.domain.model.Article
import com.period.tracker.natural.cycles.utils.Constants

/*
val id: Int,
val title: String,
val smallTitle: String? = null,
val titleColor: ArticleTitleColor = ArticleTitleColor.BLACK,
val content: String,
val bigImage: Uri,
val smallImage: Uri,
val isBookmark: Boolean = false,
val internalArticlesId: List<Int>? = null,
val type: ArticleType? = null,
val parentType: ArticleType
*/

class SaveArticleToFirebaseUseCase(private val firebaseDatabase: DatabaseReference) {
    fun execute(article: Article) {
        firebaseDatabase
            .child(Constants.FIREBASE_DATABASE_ARTICLES_NAME)
            .child(article.id.toString())
            .apply {
                child(article::title.name).setValue(article.title)
                child(article::smallTitle.name).setValue(article.smallTitle)
                child(article::titleColor.name).setValue(article.titleColor)
                child(article::content.name).setValue(article.content)
                child(article::bigImage.name).setValue(article.bigImage.toString())
                child(article::smallImage.name).setValue(article.smallImage.toString())
                child(article::type.name).setValue(article.type)
                child(article::parentType.name).setValue(article.parentType)
            }
    }
}
