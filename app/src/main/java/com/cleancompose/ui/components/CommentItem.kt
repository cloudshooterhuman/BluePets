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
package com.cleancompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cleancompose.R
import com.cleancompose.domain.models.CommentModel
import com.cleancompose.domain.models.OwnerPreviewModel
import com.cleancompose.ui.theme.BluePetsApplicationTheme
import com.cleancompose.ui.tools.DevicePreviews

@Composable
fun CommentItem(comment: CommentModel) {
    Card(
        shape = RoundedCornerShape(4),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.spacing_small)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Column {
            Row(Modifier.padding(dimensionResource(id = R.dimen.spacing_regular))) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(comment.owner.pictureUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.post_text),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                )
                Column(Modifier.padding(start = dimensionResource(id = R.dimen.spacing_regular))) {
                    Text(
                        text = comment.owner.name,
                    )
                    Text(
                        text = comment.message,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(text = comment.publishDate)
            }
        }
    }
}

@DevicePreviews
@Preview
@Composable
private fun PostListItemPreview(darkTheme: Boolean) {
    BluePetsApplicationTheme(darkTheme) {
        CommentItem(
            comment = CommentModel(
                id = "1",
                "messahe",
                "1",
                OwnerPreviewModel("1", "name", "picture"),
                "1 an",
            ),
        )
    }
}
