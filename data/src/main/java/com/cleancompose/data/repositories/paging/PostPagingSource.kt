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
package com.cleancompose.data.repositories.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cleancompose.domain.models.NetworkError
import com.cleancompose.domain.models.NetworkException
import com.cleancompose.domain.models.NetworkSuccess
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
//        Log.e("myapp", "PostPagingSource ${Thread.currentThread().name}")
        return when (val result = getPostUseCase.invoke(nextPage)) {
            is NetworkSuccess ->
                LoadResult.Page(
                    data = result.data,
                    prevKey = if (nextPage == STARTING_PAGE_INDEX) null else nextPage - 1,
                    nextKey = if (result.data.isEmpty()) null else nextPage + 1,
                )
            is NetworkError -> LoadResult.Error(Throwable(result.message))
            is NetworkException -> {
                LoadResult.Error(result.e)
            }
        }
    }
}
