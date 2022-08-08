package com.natural.cycles.period.tracker.utils

import com.natural.cycles.period.tracker.domain.model.ArticleTitleColor

object ArticleTitleColorHelper {
    fun fromString(string: String?): ArticleTitleColor? {
        return when (string) {
            ArticleTitleColor.BLACK.name -> ArticleTitleColor.BLACK
            ArticleTitleColor.WHITE.name -> ArticleTitleColor.WHITE
            else -> null
        }
    }
}