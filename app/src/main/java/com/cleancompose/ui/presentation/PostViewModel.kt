package com.cleancompose.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleancompose.core.asResult
import com.cleancompose.data.repositories.DefaultPostRepository
import com.cleancompose.domain.models.PostModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface PostUiState {
    data class Success(val posts: List<PostModel>) : PostUiState
    object Error : PostUiState
    object Loading : PostUiState
}

private const val DEFAULT_TIMEOUT = 5000L

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: DefaultPostRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(PostUiState.Loading)
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    fun fetchPosts() =
        viewModelScope.launch {
            postRepository.getPosts(0)
                .asResult()
                .map { result ->
                    when (result) {
                        is com.cleancompose.core.Result.Success -> PostUiState.Success(result.data)
                        is com.cleancompose.core.Result.Loading -> PostUiState.Loading
                        is com.cleancompose.core.Result.Error -> PostUiState.Error
                    }
                }.stateIn(
                    scope = viewModelScope,
                    initialValue = PostUiState.Loading,
                    started = SharingStarted.WhileSubscribed(DEFAULT_TIMEOUT)
                )

        }
}