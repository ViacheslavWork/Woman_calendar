package woman.calendar.every.day.health.domain.model

data class Article(
    val id: Int,
    val title: String,
    val content: String,
    val isBookmark: Boolean = false,
    val internalArticlesId: List<Int>? = null,
    val type: ArticleGroupType? = null
)

enum class ArticleGroupType {
    REPRODUCTIVE_HEALTH,
    SEX,
    YOUR_CYCLE_PHASE,
    THE_LATEST,
    LGBTQ,
    TRIPS,
    NUTRITION
}
