package com.cleancompose.ui.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cleancompose.data.repositories.paging.PostByTagPagingSource
import com.cleancompose.data.repositories.paging.PostPagingSource
import com.cleancompose.di.ApplicationScope
import com.cleancompose.di.MainDispatcher
import com.cleancompose.domain.models.PostModel
import com.cleancompose.domain.usecases.GetPostByTagUseCase
import com.cleancompose.domain.usecases.GetPostUseCase
import com.cleancompose.ui.components.base.EditableUserInputState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 20
private const val EMPTY_STRING = ""

@HiltViewModel
class PostViewModel @Inject constructor(
    @ApplicationScope val coroutineScope: CoroutineScope,
    private val postUseCase: GetPostUseCase,
    private val postByTagUseCase: GetPostByTagUseCase,
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
) : ViewModel() {
    var uiState: Flow<PagingData<PostModel>> =
        Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                PostPagingSource(
                    postUseCase
                )
            }
        ).flow
            .cachedIn(coroutineScope)
            .flowOn(mainDispatcher)

    fun getAllPosts() {
        uiState = Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                PostPagingSource(
                    postUseCase
                )
            }
        ).flow
            .cachedIn(coroutineScope)
            .flowOn(mainDispatcher)
    }

    fun getPostByTag(tagId: String) {
        uiState = Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                PostByTagPagingSource(postByTagUseCase, tagId)
            }
        ).flow
            .cachedIn(coroutineScope)
            .flowOn(mainDispatcher)
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