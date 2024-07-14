package com.shreyanshsinghks.newsappfk.di

import com.shreyanshsinghks.newsappfk.data.network.NewsApi
import com.shreyanshsinghks.newsappfk.data.repository.NewsRepositoryImpl
import com.shreyanshsinghks.newsappfk.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi): NewsRepository{
        return NewsRepositoryImpl(api)
    }

}