package com.period.tracker.natural.cycles.domain.model

import android.net.Uri

data class Article(
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
)

enum class ArticleType {
    MAIN,
    REPRODUCTIVE_HEALTH,
    SEX,
    YOUR_CYCLE_PHASE,
    THE_LATEST,
    LGBTQ,
    TIPS_FOR_FREE_PAIN,
    NUTRITION_AND_FITNESS,
    EARLY_SIGNS_OF_PREGNANCY,
    VAGINAL_DISCHARGE_COLOR,
    COVID,
    HOW_OVULATION_AFFECTS,
    SEX_AND_YOUR_CYCLE,
    HOW_TO_MAKE_SEX_PAINLESS,
    HOW_TO_GET_MORE_PLEASURE,
    WAYS_TO_DEAL_WITH_CRAMPS,
    ALL_THINGS_MOOD_SWINGS,
    BEAT_THE_BLOAT,
    TIPS_TO_RELIEVE_PMS_SYMPTOM,
    WORKOUTS_AND_YOUR_CYCLE,
    FEMALE_HEALTH_AND_NUTRITION,
}

enum class ArticleTitleColor {
    BLACK, WHITE
}
