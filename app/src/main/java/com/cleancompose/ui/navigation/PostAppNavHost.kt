package com.cleancompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cleancompose.PictureScreen
import com.cleancompose.PostScreen
import com.cleancompose.R

@Composable
fun PostAppNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
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

sealed class Screen(val route: String,   val title : Int ) {
    object Home : Screen("home", title = R.string.tolbar_title)
    object Picture : Screen("picture/{imageUrl}", title = R.string.pit_picture) {
        fun createRoute(pictureUri: String) = "picture/$pictureUri"
    }

    object InvalidScreen : Screen("invalidScreen", title = R.string.tolbar_title)


    companion object {
        fun valueOf(route :String): Screen {
            return when(route) {
                Home.route -> Home
                Picture.route -> Picture
                else -> Home
            }
        }
    }


}



