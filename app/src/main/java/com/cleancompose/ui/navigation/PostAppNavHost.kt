package com.cleancompose.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.cleancompose.PictureScreen
import com.cleancompose.PostScreen
import com.cleancompose.R
import com.cleancompose.domain.models.PostModel
import com.cleancompose.ui.navigation.Screen.Home.route

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
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



