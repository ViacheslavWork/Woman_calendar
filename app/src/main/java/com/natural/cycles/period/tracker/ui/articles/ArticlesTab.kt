package com.natural.cycles.period.tracker.ui.articles

import androidx.annotation.DrawableRes
import com.natural.cycles.period.tracker.R

enum class ArticlesTab(val title: String, @DrawableRes val icon: Int? = null) {
    DISCOVER("Discover"),
    SAVED("Saved", R.drawable.ic_saved_full),
    RECENT("Recent", R.drawable.ic_recent)
}