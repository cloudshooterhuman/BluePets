package com.cleancompose.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cleancompose.domain.ResultOf
import com.cleancompose.domain.models.PostModel
import com.cleancompose.domain.usecases.GetPostUseCase

private const val STARTING_PAGE_INDEX = 0

class PostPagingSource(private val getPostUseCase: GetPostUseCase) :
    PagingSource<Int, PostModel>() {
    override fun getRefreshKey(state: PagingState<Int, PostModel>): Int? {
        return state.anchorPosition
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