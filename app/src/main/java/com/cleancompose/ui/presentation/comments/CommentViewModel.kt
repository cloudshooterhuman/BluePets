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
package com.cleancompose.ui.presentation.comments

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleancompose.domain.models.CommentModel
import com.cleancompose.domain.models.NetworkError
import com.cleancompose.domain.models.NetworkException
import com.cleancompose.domain.models.NetworkSuccess
import com.cleancompose.domain.usecases.GetCommentsUseCase
import com.cleancompose.ui.presentation.ErrorState
import com.cleancompose.ui.presentation.ExceptionState
import com.cleancompose.ui.presentation.SuccessState
import com.cleancompose.ui.presentation.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val UNKNOWN_ERROR = "Unknown error"

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val getCommentUseCase: GetCommentsUseCase,
) : ViewModel() {
    private var viewState = MutableStateFlow<ViewState<List<CommentModel>>>(SuccessState(emptyList()))

    var loading = mutableStateOf(true)

    fun comments(postId: String): StateFlow<ViewState<List<CommentModel>>> {
        viewModelScope.launch {
            when (val state = getCommentUseCase.invoke(postId)) {
                is NetworkSuccess -> {
                    viewState.value = SuccessState(state.data)
                    loading.value = false
                }
                is NetworkError -> {
                    viewState.value = ErrorState(state.message ?: UNKNOWN_ERROR)
                    loading.value = false
                }
                is NetworkException -> {
                    viewState.value = ExceptionState(state.e)
                    loading.value = false
                }
            }
        }
        return viewState
    }
}
