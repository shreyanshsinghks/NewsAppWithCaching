package com.shreyanshsinghks.newsappfk.domain.repository

import com.shreyanshsinghks.newsappfk.data.response.NewsResponse

interface NewsRepository {
    suspend fun getNews(
        language: String,
        text: String?,
        country: String?,
        ): NewsResponse
}