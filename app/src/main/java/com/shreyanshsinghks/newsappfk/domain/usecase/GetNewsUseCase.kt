package com.shreyanshsinghks.newsappfk.domain.usecase

import com.shreyanshsinghks.newsappfk.data.response.NewsResponse
import com.shreyanshsinghks.newsappfk.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(
        language: String,
        text: String?,
        country: String?
    ): NewsResponse {
        val response = newsRepository.getNews(language, text, country)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Error fetching news")
        }
    }


}