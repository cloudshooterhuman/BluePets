package com.cleancompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.cleancompose.PostScreen

@Composable
fun PostAppNavHost(
  navController: NavHostController,
  modifier: Modifier
) {
  NavHost(
    navController = navController,
    startDestination = "home",
    modifier = modifier
  ) {
    composable("home") {
      PostScreen(navController, modifier)
    }
    /*composable(Screen.ActionMovies.route) {
      ActionMoviesScreen()
    }
    composable(Screen.AnimationMovies.route) {
      AnimationMoviesScreen()
    }*/
  }
}