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
package com.cleancompose.ui.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cleancompose.data.repositories.paging.PostByTagPagingSource
import com.cleancompose.data.repositories.paging.PostPagingSource
import com.cleancompose.di.ApplicationScope
import com.cleancompose.domain.models.PostModel
import com.cleancompose.domain.usecases.GetPostByTagUseCase
import com.cleancompose.domain.usecases.GetPostUseCase
import com.cleancompose.ui.components.base.EditableUserInputState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 20
private const val EMPTY_STRING = ""

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postUseCase: GetPostUseCase,
    private val postByTagUseCase: GetPostByTagUseCase,
) : ViewModel() {
    var uiState: Flow<PagingData<PostModel>> =
        Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                PostPagingSource(
                    postUseCase,
                )
            },
        ).flow
            .cachedIn(viewModelScope)
            .onEach {
                Timber.d("myapp", it.toString())
            }

    private fun getAllPosts() {
        uiState = Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                PostPagingSource(
                    postUseCase,
                )
            },
        ).flow
            .cachedIn(viewModelScope)
    }

    private fun getPostByTag(tagId: String) {
        uiState = Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                PostByTagPagingSource(postByTagUseCase, tagId)
            },
        ).flow
            .cachedIn(viewModelScope)
    }

    private var currentTag = EMPTY_STRING

    @OptIn(ExperimentalMaterial3Api::class)
    fun refresh(
        editableUserInputState: EditableUserInputState,
        pullToRefreshState: PullToRefreshState,
    ) {
        currentTag = editableUserInputState.text

        if (editableUserInputState.isHint || (editableUserInputState.previousText == currentTag)) return

        if (editableUserInputState.text.isNotEmpty()) {
            getPostByTag(currentTag)
            editableUserInputState.updatePreviousText(editableUserInputState.text)
        } else {
            getAllPosts()
            editableUserInputState.updatePreviousText(EMPTY_STRING)
        }
        pullToRefreshState.startRefresh() // fixme ? https://stackoverflow.com/questions/78673510/updating-flow-when-using-two-paging-source-with-compose
    }
}
