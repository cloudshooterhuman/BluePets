package com.cleancompose.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cleancompose.data.repositories.paging.PostPagingSource
import com.cleancompose.domain.models.PostModel
import com.cleancompose.domain.usecases.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 20

@HiltViewModel
class PostViewModel @Inject constructor(
    postUseCase: GetPostUseCase,
) : ViewModel() {
    val uiState: Flow<PagingData<PostModel>> =
        Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { PostPagingSource(postUseCase) }
        ).flow.cachedIn(viewModelScope)
}
