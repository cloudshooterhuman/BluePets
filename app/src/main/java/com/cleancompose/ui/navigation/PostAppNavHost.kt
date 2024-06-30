/*
 * Copyright 2024 Abdellah Selassi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cleancompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cleancompose.R
import com.cleancompose.ui.screens.PictureScreen
import com.cleancompose.ui.screens.PostScreen

@Composable
fun PostAppNavHost(
    navController: NavHostController,
    modifier: Modifier,
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
            val postId = it.arguments?.getString("postId")
            PictureScreen(imageUrl, postId ?: "")
        }
    }
}

sealed class Screen(val route: String, val title: Int) {
    object Home : Screen("home", title = R.string.tolbar_title)
    object Picture : Screen("picture/{imageUrl}/{postId}", title = R.string.post_details) {
        fun createRoute(pictureUri: String, postId: String) = "picture/$pictureUri/$postId"
    }

    object InvalidScreen : Screen("invalidScreen", title = R.string.invalid_screen)

    companion object {
        fun valueOf(route: String): Screen {
            return when (route) {
                Home.route -> Home
                Picture.route -> Picture
                else -> InvalidScreen
            }
        }
    }
}
