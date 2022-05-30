package com.period.tracker.natural.cycles.ui.articles

sealed class ArticlesEvent(val id: Int) {
    class OnArticleClick(id: Int) : ArticlesEvent(id)
}
