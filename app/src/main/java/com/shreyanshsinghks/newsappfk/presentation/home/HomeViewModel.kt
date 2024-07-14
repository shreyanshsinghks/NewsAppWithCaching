package com.shreyanshsinghks.newsappfk.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shreyanshsinghks.newsappfk.data.model.News
import com.shreyanshsinghks.newsappfk.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val getNewsUseCase: GetNewsUseCase) : ViewModel() {
    init {
        viewModelScope.launch {
            val result = getNews()
            result.forEach{
                Log.d("News", "getNews: ${it.title}")
            }
        }
    }

    suspend fun getNews(): List<News> {
        return getNewsUseCase.invoke("en", null, null).news
    }
}