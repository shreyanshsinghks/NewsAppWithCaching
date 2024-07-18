package com.shreyanshsinghks.newsappfk.data.repository

import com.shreyanshsinghks.newsappfk.data.network.NewsApi
import com.shreyanshsinghks.newsappfk.data.response.NewsResponse
import com.shreyanshsinghks.newsappfk.domain.repository.NewsRepository
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(val api: NewsApi) : NewsRepository {

    override suspend fun getNews(
        language: String,
        text: String?,
        country: String?
    ): Response<NewsResponse> {
        return api.getNews(country = country, language = language, text = text)
    }

}