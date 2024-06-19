package com.cleancompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.cleancompose.R
import com.cleancompose.ui.components.LoadingIndicator
import com.cleancompose.ui.components.NetworkErrorIndicator
import com.cleancompose.ui.components.PetPostItem
import com.cleancompose.ui.navigation.Screen
import com.cleancompose.ui.presentation.PostViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel(),
) {
    val lazyPagingPosts = viewModel.uiState.collectAsLazyPagingItems()

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        refreshing = false
    }

    val pullToRefreshState = rememberPullRefreshState(refreshing, ::refresh)

    Box(
        modifier = Modifier
            .pullRefresh(pullToRefreshState)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn {
            when (val state = lazyPagingPosts.loadState.refresh) {
                is LoadState.NotLoading -> Unit

                is LoadState.Loading -> {
                    item {
                        LoadingIndicator(modifier)
                    }
                }

                is LoadState.Error -> {
                    item {
                        NetworkErrorIndicator(
                            state.error.message ?: stringResource(R.string.unknwon_error),
                            modifier
                        ) { lazyPagingPosts.retry() }
                    }
                }
            }


            if (!refreshing) {
                if (lazyPagingPosts.itemCount > 0) {
                    items(
                        lazyPagingPosts.itemCount,
                        key = lazyPagingPosts.itemKey { it.id }) { index ->
                        lazyPagingPosts[index]?.let {
                            PetPostItem(it, {
                                val imageUrl = it.imageUrl
                                val encodedUrl = URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString())
                                navController.navigate(Screen.Picture.createRoute(encodedUrl))
                            })
                        }
                    }
                }
            }

        }

        PullRefreshIndicator(refreshing, pullToRefreshState)
    }
}