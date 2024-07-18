package com.shreyanshsinghks.newsappfk.domain.repository

import com.shreyanshsinghks.newsappfk.data.response.NewsResponse
import retrofit2.Response

interface NewsRepository {
    suspend fun getNews(
        language: String,
        text: String?,
        country: String?,
        ): Response<NewsResponse>
}