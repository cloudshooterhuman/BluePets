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
package com.cleancompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SwipeUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import com.cleancompose.ui.components.EmptyListIndicator
import com.cleancompose.ui.components.LoadingIndicator
import com.cleancompose.ui.components.NetworkErrorIndicator
import com.cleancompose.ui.components.PetPostItem
import com.cleancompose.ui.components.base.EditableUserInputState
import com.cleancompose.ui.components.base.TagEditableUserInput
import com.cleancompose.ui.components.base.remeberEditableUserInputState
import com.cleancompose.ui.navigation.Screen
import com.cleancompose.ui.presentation.PostViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
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

    Column(
        modifier = Modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Box(contentAlignment = Alignment.TopCenter) {
            val editableUserInputState = remeberEditableUserInputState(hint = "Choose tag")

            TagEditableUserInput(
                state = editableUserInputState,
                caption = "To",
                vectorImageId = R.drawable.ic_search,
            )

            val currentOnTagChanged by rememberUpdatedState(
                onTagChanged(
                    editableUserInputState,
                    viewModel,
                    pullToRefreshState,
                ),
            )

            LaunchedEffect(editableUserInputState.text) {
                snapshotFlow { editableUserInputState.text }
                    .filter { !editableUserInputState.isHint }
                    .collect {
                        currentOnTagChanged
                    }
            }

            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = pullToRefreshState,
            )
        }

        Box {
            LazyColumn(state = listState) {
                when (val state = lazyPagingPosts.loadState.refresh) {
                    is LoadState.NotLoading -> {
                        val isEmptyPostList = lazyPagingPosts.itemCount == 0
                        if (isEmptyPostList) {
                            item {
                                EmptyListIndicator(modifier)
                            }
                        }
                    }

                    is LoadState.Loading -> {
                        item {
                            LoadingIndicator(modifier)
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            NetworkErrorIndicator(
                                state.error.message ?: stringResource(R.string.unknwon_error),
                                modifier,
                            ) { lazyPagingPosts.retry() }
                        }
                    }
                }

                if (!pullToRefreshState.isRefreshing) {
                    if (lazyPagingPosts.itemCount > 0) {
                        items(
                            lazyPagingPosts.itemCount,
                            key = lazyPagingPosts.itemKey { it.id },
                        ) { index ->
                            lazyPagingPosts[index]?.let {
                                PetPostItem(it, {
                                    val imageUrl = it.imageUrl
                                    val encodedUrl =
                                        URLEncoder.encode(
                                            imageUrl,
                                            StandardCharsets.UTF_8.toString(),
                                        )
                                    navController.navigate(
                                        Screen.Picture.createRoute(
                                            encodedUrl,
                                            it.id,
                                        ),
                                    )
                                })
                            }
                        }
                    }
                }
            }

            if (showUpButton) {
                SmallFloatingActionButton(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
                    onClick = {
                        scope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.SwipeUp,
                        contentDescription = stringResource(id = R.string.publication_date),
                        modifier = Modifier.size(32.dp),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun onTagChanged(
    editableUserInputState: EditableUserInputState,
    viewModel: PostViewModel,
    pullToRefreshState: PullToRefreshState,
) {
    viewModel.refresh(editableUserInputState, pullToRefreshState)
}
