package com.cleancompose.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cleancompose.data.repositories.DefaultPostRepository
import com.cleancompose.data.repositories.PostPagingSource
import com.cleancompose.domain.models.PostModel
import com.cleancompose.domain.repositories.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val ITEMS_PER_PAGE = 20
class GetPostUseCase @Inject constructor(private val postRepository: PostsRepository) {
    fun invoke(): Flow<PagingData<PostModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { PostPagingSource(postRepository) }
        ).flow
    }
}