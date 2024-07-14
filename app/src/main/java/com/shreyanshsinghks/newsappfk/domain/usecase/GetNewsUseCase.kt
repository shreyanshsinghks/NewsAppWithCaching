package com.shreyanshsinghks.newsappfk.domain.usecase

import com.shreyanshsinghks.newsappfk.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(
        language: String,
        text: String?,
        country: String?
    ) = newsRepository.getNews(language, text, country)


}