package com.natural.cycles.period.tracker.domain.usecase.firebase.articles

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.natural.cycles.period.tracker.domain.model.Article
import com.natural.cycles.period.tracker.domain.model.ArticleTitleColor
import com.natural.cycles.period.tracker.domain.model.ArticleType
import com.natural.cycles.period.tracker.utils.ArticleTitleColorHelper
import com.natural.cycles.period.tracker.utils.ArticleTypeHelper
import com.natural.cycles.period.tracker.utils.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

class GetArticlesFlowFromFirebaseUseCase(private val firebaseDatabaseReference: DatabaseReference) {
    suspend fun execute() = withContext(Dispatchers.IO) {
        val articlesFlow = MutableStateFlow<List<Article>?>(null)
        val scope = CoroutineScope(Dispatchers.IO)
        firebaseDatabaseReference
            .child(Constants.FIREBASE_DATABASE_ARTICLES_NAME)
            .get()
            .addOnSuccessListener { dataSnapshot ->
                dataSnapshot.value?.let { value ->
                    val idToArticleMap = value as HashMap<*, *>
                    scope.launch {
                        val articles = idToArticleMap.map {
                            val articleId = it.key.toString().toInt()
                            val parameterNameToParameterMap = it.value as HashMap<String?, String?>
                            getArticleFromMapOfParameters(articleId, parameterNameToParameterMap)
                        }
                        articlesFlow.emit(articles)
                        Timber.d("Articles: added = $articles")
                        cancel()
                    }
                }
            }.addOnFailureListener {
                scope.launch {
                    articlesFlow.emit(null)
                    Timber.d("Articles: failure")
                    cancel()
                }
            }
        return@withContext articlesFlow

    }

    private fun getArticleFromMapOfParameters(
        articleId: Int,
        parameters: Map<String?, String?>
    ): Article {
        val storageRef = Firebase.storage.reference
        storageRef.child("one.jpg")
            .downloadUrl
            .addOnSuccessListener {
                Timber.d("url: $it")
            }.addOnFailureListener {
                Timber.d("failure url: $it")
            }

        return Article(
            id = articleId,
            title = parameters[Article::title.name] ?: "",
            smallTitle = parameters[Article::smallTitle.name],
            titleColor = ArticleTitleColorHelper.fromString(parameters[Article::titleColor.name])
                ?: ArticleTitleColor.BLACK,
            content = parameters[Article::content.name] ?: "",
            bigImage = Uri.parse(parameters[Article::bigImage.name] ?: ""),
            smallImage = Uri.parse(parameters[Article::smallImage.name] ?: ""),
            type = ArticleTypeHelper.fromString(parameters[Article::type.name]),
            parentType = ArticleTypeHelper.fromString(parameters[Article::parentType.name])
                ?: ArticleType.THE_LATEST
        )
    }
}