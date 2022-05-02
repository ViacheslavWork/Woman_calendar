package woman.calendar.every.day.health.ui.articles

import androidx.annotation.DrawableRes
import woman.calendar.every.day.health.R

enum class ArticlesTab(val title: String, @DrawableRes val icon: Int? = null) {
    DISCOVER("Discover"),
    SAVED("Saved", R.drawable.ic_saved_full),
    RECENT("Recent", R.drawable.ic_recent)
}