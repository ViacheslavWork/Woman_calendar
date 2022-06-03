package com.period.tracker.natural.cycles.utils

import com.period.tracker.natural.cycles.domain.model.ArticleTitleColor

object ArticleTitleColorHelper {
    fun fromString(string: String?): ArticleTitleColor? {
        return when (string) {
            ArticleTitleColor.BLACK.name -> ArticleTitleColor.BLACK
            ArticleTitleColor.WHITE.name -> ArticleTitleColor.WHITE
            else -> null
        }
    }
}