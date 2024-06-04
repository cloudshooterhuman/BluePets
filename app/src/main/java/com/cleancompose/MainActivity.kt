package com.cleancompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cleancompose.domain.models.PostModel
import com.cleancompose.ui.navigation.PostAppNavHost
import com.cleancompose.ui.presentation.PostUiState
import com.cleancompose.ui.presentation.PostViewModel
import com.cleancompose.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PostAppNavHost(
                        navController = navController,
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PostScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    val uiState: PostUiState by viewModel.uiState.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 16.dp
        )
    ) {


        when (val postState = uiState) {
            PostUiState.Error -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        "Error fetching posts",
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                }
            }

            PostUiState.Loading -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    //LoadingIndicator()
                    Text(
                        "Loading ...",
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                }
            }

            is PostUiState.Success -> {
                items(postState.posts) { movie ->
                    PostImage(movie)
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun PostImage(post: PostModel) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(post.imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = post.text,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.ic_launcher_background)
    )
}
