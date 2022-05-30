package com.period.tracker.natural.cycles.ui.articles

import androidx.annotation.DrawableRes
import com.period.tracker.natural.cycles.R

enum class ArticlesTab(val title: String, @DrawableRes val icon: Int? = null) {
    DISCOVER("Discover"),
    SAVED("Saved", R.drawable.ic_saved_full),
    RECENT("Recent", R.drawable.ic_recent)
}