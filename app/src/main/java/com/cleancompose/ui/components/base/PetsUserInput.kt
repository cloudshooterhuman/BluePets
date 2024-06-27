package com.cleancompose.ui.components.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cleancompose.R
import com.cleancompose.ui.theme.BluePetsApplicationTheme
import com.cleancompose.ui.theme.captionTextStyle


@Composable
fun PetsBaseUserInput(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null,
    showCaption: () -> Boolean = { true },
    tintIcon: () -> Boolean,
    tint: Color = Color.DarkGray,
    content: @Composable () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 6.dp,
                top = 1.dp,
                end = 6.dp,
                bottom = 6.dp
            )
    ) {
        Surface(
            modifier = modifier,
            onClick = onClick,
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            Row(Modifier.padding(all = 12.dp)) {
                if (vectorImageId != null) {
                    Icon(
                        modifier = Modifier.size(24.dp, 24.dp),
                        painter = painterResource(id = vectorImageId),
                        tint = if (tintIcon()) tint else Color.Black,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                }
                if (caption != null && showCaption()) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = caption,
                        style = (captionTextStyle).copy(color = tint)
                    )
                    Spacer(Modifier.width(8.dp))
                }
                Row(
                    Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                ) {
                    content()
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewInput() {
    BluePetsApplicationTheme {
        Surface {
            PetsBaseUserInput(
                tintIcon = { true },
                vectorImageId = R.drawable.ic_search,
                caption = "Caption",
                showCaption = { true }
            ) {
                Text(text = "text", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}