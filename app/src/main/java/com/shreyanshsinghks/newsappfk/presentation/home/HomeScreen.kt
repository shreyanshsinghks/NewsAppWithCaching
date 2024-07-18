package com.shreyanshsinghks.newsappfk.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.shreyanshsinghks.newsappfk.data.model.News
import com.shreyanshsinghks.newsappfk.presentation.State

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState = viewModel.state.collectAsState()
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        SearchBar(text = searchText, onSearch = {
            searchText = it
            viewModel.getNews(it)
        })
        Spacer(modifier = Modifier.height(16.dp))

        when (uiState.value) {
            is State.Loading -> {
                // Show loading indicator
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Button(onClick = { viewModel.getNews() }) {
                        Text(text = "Retry")
                    }
                }
            }

            is State.Success -> {
                val data = (uiState.value as State.Success).data
                // Show data
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Text(text = "News")
                    }
                    items(data.news) { article ->
                        NewsItem(news = article)
                    }
                }
            }

            is State.Error -> {
                // Show error message
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = (uiState.value as State.Error).message)
                    Button(onClick = { viewModel.getNews(searchText) }) {
                        Text(text = "Retry")
                    }
                }

            }
        }
    }
}


@Composable
fun NewsItem(news: News) {
    Box(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxSize()
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray.copy(alpha = 0.2f))
    ) {
        if (!news.image.isNullOrBlank()) {
            AsyncImage(
                model = news.image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = news.title ?: "",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            Text(
                text = news.publish_date ?: "",
                color = Color.White,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
            Text(
                text = news.author ?: "",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }

    }
}

@Composable
fun SearchBar(text: String, onSearch: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = text, onValueChange = onSearch,
            label = { Text(text = "Search") },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black)
        )
        Image(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            modifier = Modifier
                .align(
                    Alignment.CenterEnd
                )
                .padding(top = 8.dp, end = 8.dp)
        )
    }
}