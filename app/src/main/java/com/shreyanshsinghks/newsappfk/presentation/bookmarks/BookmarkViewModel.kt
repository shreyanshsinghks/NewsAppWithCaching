package com.shreyanshsinghks.newsappfk.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shreyanshsinghks.newsappfk.data.database.NewsDatabase
import com.shreyanshsinghks.newsappfk.data.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookmarkViewModel @Inject constructor(database: NewsDatabase) : ViewModel() {
    private val newsDao = database.newsDao()

    fun getBookmarks() = newsDao.getNews()


}