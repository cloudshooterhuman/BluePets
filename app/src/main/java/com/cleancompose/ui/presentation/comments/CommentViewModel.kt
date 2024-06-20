package com.cleancompose.ui.presentation.comments

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleancompose.domain.ResultOf
import com.cleancompose.domain.models.CommentModel
import com.cleancompose.domain.usecases.GetCommentsUseCase
import com.cleancompose.ui.presentation.Error
import com.cleancompose.ui.presentation.Success
import com.cleancompose.ui.presentation.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val getCommentUseCase: GetCommentsUseCase,
) : ViewModel() {
    private var viewState = MutableStateFlow<ViewState<List<CommentModel>>>(Success(emptyList()))

    var loading = mutableStateOf(true)

    fun comments(postId: String): StateFlow<ViewState<List<CommentModel>>> {
        viewModelScope.launch {
            when (val state = getCommentUseCase.invoke(postId)) {
                is ResultOf.Success -> {
                    viewState.value = Success(state.value)
                    loading.value = false
                }

                is ResultOf.Failure -> {
                    viewState.value = Error(state.throwable)
                    loading.value = false
                }
            }
        }
        return viewState
    }
}