package com.cleancompose.ui.components.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class EditableUserInputState(val hint: String, initialText: String) {

    var text by mutableStateOf(initialText)
        private set

    var previousText by mutableStateOf("")
        private set

    fun updateText(newText : String) {
        text = newText
    }

    fun updatePreviousText(newText : String) {
        previousText = newText
    }

    val isHint : Boolean
        get() = text == hint

    companion object {
        val Saver: Saver<EditableUserInputState, *> = listSaver(
            save = { listOf(it.hint, it.text, it.previousText) },
            restore = {
                EditableUserInputState(
                    hint = it[0],
                    initialText = it[1]
                )
            }
        )
    }

}

@Composable
fun remeberEditableUserInputState(hint : String, previousText: String = "") : EditableUserInputState =
    rememberSaveable (hint, saver = EditableUserInputState.Saver) {
        EditableUserInputState(hint, hint)
    }
