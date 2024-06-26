package com.cleancompose.ui.components.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.SolidColor
import com.cleancompose.ui.theme.captionTextStyle

@Composable
fun TagEditableUserInput(
    state: EditableUserInputState = remeberEditableUserInputState(hint = ""),
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null
) {


    PetsBaseUserInput(
        showCaption = { false },
        caption = caption,
        tintIcon = { !state.isHint },
        //showCaption = { !isHint() },
        vectorImageId = vectorImageId
    ) {
        BasicTextField(
            singleLine = true,
            value = state.text,
            onValueChange = {
                state.updateText(it)
            },
            textStyle = if (state.isHint) {
                captionTextStyle.copy(color = LocalContentColor.current)
            } else {
                MaterialTheme.typography.bodyMedium.copy(color = LocalContentColor.current)
            },
            cursorBrush = SolidColor(LocalContentColor.current)
        )
    }
}