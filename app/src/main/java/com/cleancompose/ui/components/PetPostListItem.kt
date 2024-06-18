package com.cleancompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cleancompose.R
import com.cleancompose.domain.models.OwnerPreviewModel
import com.cleancompose.domain.models.PostModel
import com.cleancompose.ui.theme.BluePetsApplicationTheme
import com.cleancompose.ui.tools.DevicePreviews

@Composable
fun PetPostListItem(
    post: PostModel,
    onClick: () -> Unit,
    elevation: Dp = 8.dp,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium,
    iconSize: Dp = 16.dp,
) {
    Card(
        shape = RoundedCornerShape(4),
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .height(180.dp)
            .padding(start = 3.dp, end = 3.dp, top = 3.dp),
        elevation = elevation
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(post.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = post.text,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.avatar_size))
            )
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
            ) {
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.PhotoCamera,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = stringResource(id = R.string.camera),
                        modifier = Modifier.size(iconSize)
                    )
                    Text(
                        text = post.owner.name,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start)
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(post.owner.pictureUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = post.text,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.ic_launcher_background),
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                    )

                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.AccessTime,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = stringResource(id = R.string.publication_date),
                        modifier = Modifier.size(iconSize)
                    )
                    Text(
                        text = post.publishDate,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start)
                    )
                }

                Text(
                    text = post.text,
                    style = MaterialTheme.typography.bodyMedium,
                    //maxLines = 2,
                    //overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 8.dp, bottom = 4.dp)
                )
            }
        }
    }
}


@DevicePreviews
@Preview
@Composable
private fun PostListItemPreview(darkTheme: Boolean) {
    BluePetsApplicationTheme(darkTheme) {
        PetPostListItem(
            post = PostModel(
                "1",
                "Dogma Item",
                "http",
                "24 mai 2020 14:30",
                OwnerPreviewModel("1", "Abdel", "http")
            ),
            onClick = {}
        )
    }
}