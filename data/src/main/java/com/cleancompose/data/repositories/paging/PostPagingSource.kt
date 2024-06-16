package com.cleancompose.data.repositories.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cleancompose.domain.ResultOf
import com.cleancompose.domain.models.PostModel
import com.cleancompose.domain.usecases.GetPostUseCase

private const val STARTING_PAGE_INDEX = 0

class PostPagingSource(private val getPostUseCase: GetPostUseCase) :
    PagingSource<Int, PostModel>() {

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, PostModel>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostModel> {
        val nextPage: Int = params.key ?: STARTING_PAGE_INDEX
        return when (val result = getPostUseCase.invoke(nextPage)) {
            is ResultOf.Success ->
                LoadResult.Page(
                    data = result.value,
                    prevKey = if (nextPage == STARTING_PAGE_INDEX) null else nextPage - 1,
                    nextKey = if (result.value.isEmpty()) null else nextPage + 1
                )

            is ResultOf.Failure -> LoadResult.Error(Throwable(result.throwable.message))
        }
    }

}