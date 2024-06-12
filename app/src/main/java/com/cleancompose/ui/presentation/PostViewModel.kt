package com.cleancompose.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.cleancompose.domain.models.PostModel
import com.cleancompose.domain.usecases.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    postUseCase: GetPostUseCase,
) : ViewModel() {
    val uiState: Flow<PagingData<PostModel>> = postUseCase.invoke()
}
