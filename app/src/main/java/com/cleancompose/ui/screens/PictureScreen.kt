package com.cleancompose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
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
import com.cleancompose.ui.presentation.Error
import com.cleancompose.ui.presentation.Loading
import com.cleancompose.ui.presentation.Success
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
            placeholder = painterResource(id = R.drawable.ic_launcher_background)
        )

        if (loadingState.value) {
            LoadingIndicator(modifier = Modifier)
        } else {
            commentState.value.let {
                when (it) {
                    is Error -> NetworkErrorIndicator(
                        message = it.error.message ?: stringResource(id = R.string.unknwon_error),
                        modifier = Modifier
                    ) {
                        Unit
                    }

                    is Loading -> LoadingIndicator(modifier = Modifier)
                    is Success -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(it.data) {
                                CommentItem(it)
                            }
                        }
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

