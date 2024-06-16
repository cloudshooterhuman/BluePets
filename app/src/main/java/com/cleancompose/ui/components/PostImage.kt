package com.cleancompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cleancompose.R
import com.cleancompose.domain.models.PostModel
import com.cleancompose.ui.navigation.Screen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun PostImage(post: PostModel, navController: NavController) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(post.imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = post.text,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.ic_launcher_background),
        modifier = Modifier.clickable(onClick = {
            val imageUrl = post.imageUrl
            val encodedUrl = URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString())
            navController.navigate(Screen.Picture.createRoute(encodedUrl))
        })
    )
}