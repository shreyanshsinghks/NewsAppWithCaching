package com.shreyanshsinghks.newsappfk.data.response

import com.shreyanshsinghks.newsappfk.data.model.News

data class NewsResponse(
    val available: Int,
    val news: List<News>,
    val number: Int,
    val offset: Int
)