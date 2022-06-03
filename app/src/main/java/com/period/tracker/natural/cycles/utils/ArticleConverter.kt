package com.period.tracker.natural.cycles.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.period.tracker.natural.cycles.domain.model.Article

object ArticleConverter {
    @TypeConverter
    fun articleToJson(value: Article?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToArticle(value: String) = Gson().fromJson(value, Article::class.java)
}