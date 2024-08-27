package com.shreyanshsinghks.newsappfk.presentation.bookmarks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shreyanshsinghks.newsappfk.data.model.News
import com.shreyanshsinghks.newsappfk.presentation.home.NewsItemHome
import com.shreyanshsinghks.newsappfk.utils.NavRoutes


@Composable
fun BookmarkScreen(navHostController: NavHostController) {
    val viewModel: BookmarkViewModel = hiltViewModel()
    val bookmarks = viewModel.getBookmarks().collectAsState(initial = listOf())
    Scaffold { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp)
            ) {
                Text(text = "Bookmarks", fontSize = 30.sp)
                Spacer(modifier = Modifier.size(16.dp))
                NewsListView(news = bookmarks.value) {
                    navHostController.navigate(NavRoutes.createNewsDetailsRoute(it))
                }
            }
        }
    }
}


@Composable
fun NewsListView(news: List<News>, onClick: (News) -> Unit) {
    LazyColumn {
        items(news) { article ->
            NewsItemHome(news = article) {
                onClick(article)
            }
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}