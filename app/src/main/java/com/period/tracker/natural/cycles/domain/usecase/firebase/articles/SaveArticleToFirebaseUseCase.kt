package com.period.tracker.natural.cycles.domain.usecase.firebase.articles

import com.google.firebase.database.DatabaseReference
import com.period.tracker.natural.cycles.domain.model.Article
import com.period.tracker.natural.cycles.utils.Constants

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
