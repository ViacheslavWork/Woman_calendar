package woman.calendar.every.day.health.ui.articles

sealed class ArticlesEvent(val id: Int) {
    class OnArticleClick(id: Int) : ArticlesEvent(id)
}
