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
    @DrawableRes vectorImageId: Int? = null,
) {
    PetsBaseUserInput(
        showCaption = { false },
        caption = caption,
        tintIcon = { !state.isHint },
        // showCaption = { !isHint() },
        vectorImageId = vectorImageId,
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
            cursorBrush = SolidColor(LocalContentColor.current),
        )
    }
}
