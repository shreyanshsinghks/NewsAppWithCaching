package com.shreyanshsinghks.newsappfk.presentation.news_details


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.shreyanshsinghks.newsappfk.data.model.News
import com.shreyanshsinghks.newsappfk.presentation.State
import com.shreyanshsinghks.newsappfk.presentation.bookmarks.BookmarkViewModel

@Composable
fun NewsDetailsScreen(
    news: News,
    onBackPress: () -> Unit,
    onBookmarkToggle: (Boolean) -> Unit
) {
    val bookMarkViewModel: BookmarkViewModel = hiltViewModel()
    val bookMarkState = bookMarkViewModel.getBookmarks().collectAsState(initial = listOf())
    var isBookmarked = remember(bookMarkState.value) {
        bookMarkState.value.any { it.title == news.title }
    }
    val viewModel: NewsDetailsViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        if (state.value is State.Success) {
            Toast.makeText(context, "News saved successfully", Toast.LENGTH_SHORT).show()
        } else {
//            Toast.makeText(context, "${state.value}", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            NewsTopAppBar(
                title = news.title ?: "News Details",
                onBackPress = onBackPress,
                isBookmarked = isBookmarked,
                onBookmarkToggle = {
                    isBookmarked = !isBookmarked
                    onBookmarkToggle(isBookmarked)
                    if (isBookmarked) {
                        viewModel.addNews(news = news)
                    } else {
                        viewModel.deleteBookmark(news = news)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            NewsImage(imageUrl = news.image)
            NewsContent(news = news)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(
    title: String,
    onBackPress: () -> Unit,
    isBookmarked: Boolean,
    onBookmarkToggle: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        navigationIcon = {
            IconButton(onClick = onBackPress) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = onBookmarkToggle, modifier = Modifier.clickable {

            }) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                    contentDescription = if (isBookmarked) "Remove Bookmark" else "Add Bookmark"
                )
            }
        }
    )
}

@Composable
fun NewsImage(imageUrl: String?) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "News Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun NewsContent(news: News) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = news.publish_date ?: "No date",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = news.title ?: "No title",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "By ${news.authors?.joinToString(", ") ?: "Unknown"}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = news.text ?: "No content",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}