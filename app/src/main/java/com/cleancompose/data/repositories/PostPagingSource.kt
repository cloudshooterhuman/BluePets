package com.cleancompose.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cleancompose.domain.models.PostModel
import com.cleancompose.domain.repositories.PostsRepository
import kotlinx.coroutines.flow.last

private const val STARTING_PAGE_INDEX = 0
class PostPagingSource(private val postRepository: PostsRepository)
    : PagingSource<Int, PostModel>() {
    override fun getRefreshKey(state: PagingState<Int, PostModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostModel> {
        return try {
            val nextPage: Int = params.key ?: STARTING_PAGE_INDEX
            val posts = postRepository.getPosts(nextPage)
            LoadResult.Page(
                data = posts.last(),
                prevKey = if (nextPage == STARTING_PAGE_INDEX) null else nextPage - 1,
                nextKey = if (posts.last().isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}