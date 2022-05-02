package woman.calendar.every.day.health.ui.articles

sealed class ArticlesEvent(val id: Int) {
    class OnGroupClick(id: Int) : ArticlesEvent(id)
}
