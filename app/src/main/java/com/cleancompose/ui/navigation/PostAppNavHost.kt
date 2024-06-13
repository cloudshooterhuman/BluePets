package com.cleancompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cleancompose.PictureScreen
import com.cleancompose.PostScreen

@Composable
fun PostAppNavHost(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {

        composable(Screen.Home.route) {
            PostScreen(navController, modifier)
        }

        composable(Screen.Picture.route) {
            val imageUrl = it.arguments?.getString("imageUrl")
            PictureScreen(imageUrl)
        }

    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Picture : Screen("picture/{imageUrl}") {
        fun createRoute(pictureUri: String) = "picture/$pictureUri"
    }
}



