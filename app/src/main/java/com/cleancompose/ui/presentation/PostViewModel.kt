package com.cleancompose.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleancompose.core.Result
import com.cleancompose.core.asResult
import com.cleancompose.domain.usecases.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


private const val DEFAULT_TIMEOUT = 5000L

@HiltViewModel
class PostViewModel @Inject constructor(
    postUseCase: GetPostUseCase
) : ViewModel() {

    val uiState = postUseCase.invoke(0)
        .asResult()
        .map { result ->
            when (result) {
                is Result.Success -> PostUiState.Success(result.data)
                is Result.Loading -> PostUiState.Loading
                is Result.Error -> PostUiState.Error
            }
        }.stateIn(
            scope = viewModelScope,
            initialValue = PostUiState.Loading,
            started = SharingStarted.WhileSubscribed(DEFAULT_TIMEOUT)
        )
}
