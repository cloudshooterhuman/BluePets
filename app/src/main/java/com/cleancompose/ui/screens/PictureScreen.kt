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

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cleancompose.R
import com.cleancompose.ui.components.CommentItem
import com.cleancompose.ui.components.LoadingIndicator
import com.cleancompose.ui.components.NetworkErrorIndicator
import com.cleancompose.ui.presentation.ErrorState
import com.cleancompose.ui.presentation.ExceptionState
import com.cleancompose.ui.presentation.LoadingState
import com.cleancompose.ui.presentation.SuccessState
import com.cleancompose.ui.presentation.comments.CommentViewModel
import com.cleancompose.ui.theme.BluePetsApplicationTheme

@Composable
fun PictureScreen(
    imageUrl: String?,
    postId: String,
    commentsViewModel: CommentViewModel = hiltViewModel(),
) {
    val commentState = commentsViewModel.comments(postId).collectAsStateWithLifecycle()
    val loadingState = commentsViewModel.loading

    Column {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.post_text),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
        )

        if (loadingState.value) {
            LoadingIndicator(modifier = Modifier)
        } else {
            commentState.value.let { comment ->
                when (comment) {
                    is ErrorState -> NetworkErrorIndicator(
                        message = comment.message,
                        modifier = Modifier,
                    ) {
                        Unit
                    }

                    is LoadingState -> LoadingIndicator(modifier = Modifier)
                    is SuccessState -> {
                        SideEffect {
                            Log.e("myapp", "comment.data ${comment.data.size}")
                        }
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(comment.data) {
                                CommentItem(it)
                            }
                        }
                    }

                    is ExceptionState -> NetworkErrorIndicator(
                        message = comment.error.message
                            ?: stringResource(id = R.string.unknwon_error),
                        modifier = Modifier,
                    ) {
                        Unit
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun CommentStateProfilePreview() {
    BluePetsApplicationTheme {
        PictureScreen("10", "1")
    }
}
