package com.shreyanshsinghks.newsappfk.data.repository

import com.shreyanshsinghks.newsappfk.data.network.NewsApi
import com.shreyanshsinghks.newsappfk.data.response.NewsResponse
import com.shreyanshsinghks.newsappfk.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(val api: NewsApi) : NewsRepository {

    override suspend fun getNews(language: String, text: String?, country: String?): NewsResponse {
        val response = api.getNews(country = country, language = language, text = text)
        return response.body()!!
    }

}