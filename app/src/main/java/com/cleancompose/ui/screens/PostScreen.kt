package com.cleancompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material.icons.rounded.SwipeUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel(),
) {
    val lazyPagingPosts = viewModel.uiState.collectAsLazyPagingItems()

    var itemCount by remember { mutableIntStateOf(15) }
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            // fetch something
            delay(1500)
            itemCount += 5
            pullToRefreshState.endRefresh()
        }
    }

    val listState = rememberLazyListState()
    val showUpButton by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(state = listState) {
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


            if (!pullToRefreshState.isRefreshing) {
                if (lazyPagingPosts.itemCount > 0) {
                    items(
                        lazyPagingPosts.itemCount,
                        key = lazyPagingPosts.itemKey { it.id }) { index ->
                        lazyPagingPosts[index]?.let {
                            PetPostItem(it, {

                                val imageUrl = it.imageUrl
                                val encodedUrl =
                                    URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString())
                                navController.navigate(
                                    Screen.Picture.createRoute(
                                        encodedUrl,
                                        it.id
                                    )
                                )
                            })
                        }
                    }
                }
            }

        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState,
        )

        if (showUpButton) {
            SmallFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(8.dp),
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }) {
                Icon(
                    imageVector = Icons.Rounded.SwipeUp,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(id = R.string.publication_date),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}