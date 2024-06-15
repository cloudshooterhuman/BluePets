package com.cleancompose.ui.presentation

import androidx.compose.runtime.Immutable
import com.cleancompose.domain.models.PostModel

@Immutable
sealed interface PostUiState {
    data class Success(val posts: com.cleancompose.domain.models.PostModel) : PostUiState
    object Error : PostUiState
    object Loading : PostUiState
}