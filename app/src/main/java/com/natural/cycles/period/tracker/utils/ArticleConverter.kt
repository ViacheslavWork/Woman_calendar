package com.natural.cycles.period.tracker.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.natural.cycles.period.tracker.domain.model.Article

object ArticleConverter {
    @TypeConverter
    fun articleToJson(value: Article?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToArticle(value: String) = Gson().fromJson(value, Article::class.java)
}