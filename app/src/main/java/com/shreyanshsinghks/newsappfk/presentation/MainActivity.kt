package com.shreyanshsinghks.newsappfk.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shreyanshsinghks.newsappfk.presentation.bookmarks.BookmarkScreen
import com.shreyanshsinghks.newsappfk.presentation.home.HomeScreen
import com.shreyanshsinghks.newsappfk.presentation.news_details.NewsDetailsScreen
import com.shreyanshsinghks.newsappfk.ui.theme.NewsAppFKTheme
import com.shreyanshsinghks.newsappfk.utils.NavRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppFKTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "/home") {
                    composable("/home") {
                        HomeScreen(navController = navController)
                    }
                    composable("news_details/news={news}") { backStackEntry ->
                        val newsJson = backStackEntry.arguments?.getString("news")
                        val news = newsJson?.let { NavRoutes.getNewsFromRoute(it) }
                        news?.let {
                            NewsDetailsScreen(
                                news = it,
                                onBookmarkToggle = {},
                                onBackPress = {
                                    navController.navigateUp()
                                })
                        }
                    }
                    composable("/bookmarks"){
                        BookmarkScreen(navHostController = navController)
                    }
                }

            }
        }
    }
}