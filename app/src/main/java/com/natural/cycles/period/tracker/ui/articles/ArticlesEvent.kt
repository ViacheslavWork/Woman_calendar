package com.natural.cycles.period.tracker.ui.articles

sealed class ArticlesEvent(val id: Int) {
    class OnArticleClick(id: Int) : ArticlesEvent(id)
}
