package com.cleancompose.ui.presentation

import com.cleancompose.domain.models.PostModel

sealed interface PostUiState {
    data class Success(val posts: List<PostModel>) : PostUiState
    object Error : PostUiState
    object Loading : PostUiState
}